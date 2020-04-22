package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.FavoritesAdapter;
import com.tracker.data.FirebaseDb;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritosFragment extends Fragment {

    private Context mContext;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private FavoritesAdapter favAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecycler(view);
        getFollowingSeries();

        ImageButton sortAdded = view.findViewById(R.id.added_button);
        ImageButton sortName = view.findViewById(R.id.name_button);
        ImageButton sortLastWatched = view.findViewById(R.id.watched_button);

        // TODO: 22/04/20 Reverse sorting //Collections.reverse(list);
        sortAdded.setOnClickListener(v -> {
            sortSeasonByAdded(mFavs);
            favAdapter.notifyDataSetChanged();
        });
        sortName.setOnClickListener(v -> {
            sortSeasonByName(mFavs);
            favAdapter.notifyDataSetChanged();
        });
        sortLastWatched.setOnClickListener(v -> {
            sortSeasonByLastWatched(mFavs);
            favAdapter.notifyDataSetChanged();
        });

    }

    private void sortSeasonByAdded(List<SerieResponse.Serie> favs) {
        Collections.sort(favs, (fav1, fav2) -> {
            String added1 = String.valueOf(fav1.addedDate);
            String added2 = String.valueOf(fav2.addedDate);
            return added1.compareTo(added2);
        });
    }

    private void sortSeasonByName(List<SerieResponse.Serie> favs) {
        Collections.sort(favs, (fav1, fav2) -> {
            String name1 = String.valueOf(fav1.name);
            String name2 = String.valueOf(fav2.name);
            return name1.compareTo(name2);
        });
    }

    private void sortSeasonByLastWatched(List<SerieResponse.Serie> favs) {
        getLastWatched(favs);
        Collections.sort(favs, (fav1, fav2) -> {
            if (fav1.lastEpisodeWatched != null && fav2.lastEpisodeWatched != null) {
                Date last1 = fav1.lastEpisodeWatched.watchedDate;
                Date last2 = (fav2.lastEpisodeWatched.watchedDate);
                return last2.compareTo(last1);
            }
            return (fav1.lastEpisodeWatched == null) ? 1 : -1;
        });
    }

    private void getLastWatched(List<SerieResponse.Serie> favs) {
        Date fechaMin = new GregorianCalendar(1900, 1, 1).getTime();
        for (SerieResponse.Serie ser : favs) {
            Episode mostSeasonRecent = new Episode();
            mostSeasonRecent.watchedDate = fechaMin;
            for (Season s : ser.seasons) {
                Episode d = s.episodes
                        .stream()
                        .filter(x -> x.watchedDate != null)
                        .collect(Collectors.toList())
                        .stream()
                        .max(Comparator.comparing(episode -> episode.watchedDate))
                        .orElse(null);

                if (d != null && d.watchedDate.after(mostSeasonRecent.watchedDate))
                    mostSeasonRecent = d;
            }
            if (mostSeasonRecent.watchedDate != fechaMin)
                ser.lastEpisodeWatched = mostSeasonRecent;
        }
    }


    private void getFollowingSeries() {
        FirebaseDb.getInstance().getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                List<SerieResponse.Serie> favTemp = dataSnapshot.getValue(new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                });
                if (favTemp != null) {
                    mFavs.addAll(favTemp);
                    favAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Sin implementar
            }
        });
    }

    private void setRecycler(@NonNull View view) {
        favAdapter = new FavoritesAdapter(getActivity(), mFavs);
        RecyclerView rvFavs = view.findViewById(R.id.grid_favoritas);
        rvFavs.setHasFixedSize(true);
        rvFavs.setItemViewCacheSize(20);
        rvFavs.setSaveEnabled(true);
        rvFavs.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvFavs.setAdapter(favAdapter);
    }
}
