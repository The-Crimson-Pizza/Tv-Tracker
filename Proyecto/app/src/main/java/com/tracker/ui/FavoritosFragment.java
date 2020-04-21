package com.tracker.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.FavoritesAdapter;
import com.tracker.data.FirebaseDb;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

public class FavoritosFragment extends Fragment {

    private Context mContext;
    private DatabaseReference databaseRef;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private FavoritesAdapter favAdapter;
    private RecyclerView rvFavs;


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

        sortAdded.setOnClickListener(v -> {

        });

        sortName.setOnClickListener(v -> {

        });

        sortLastWatched.setOnClickListener(v -> {

        });


    }

    private void sortSeasonByAdded(List<SerieResponse.Serie> favs) {
        Collections.sort(favs, (fav1, fav2) -> {
            String name1 = String.valueOf(fav1.addedDate);
            String name2 = String.valueOf(fav2.addedDate);
            return name1.compareTo(name2);
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
        Collections.sort(favs, (fav1, fav2) -> {
            String name1 = String.valueOf(fav1.name);
            String name2 = String.valueOf(fav2.name);
            return name1.compareTo(name2);
        });
    }

    private void getLastwatched() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<Episode> lastEpis = new ArrayList<>();
            HashMap<String, Date> fechas = new HashMap<>();
            for (SerieResponse.Serie ser : mFavs) {
                for (Season s : ser.seasons) {
                    Episode last = null;

                    last = s.episodes.stream().max(Comparator.comparing(Episode::getWatchedDate)).get();

                    fechas.put(ser.name, last.watchedDate);
//                lastEpis.add(last);
                }
            }
        }
    }

//    private void sortHash() {
//        Map<Integer, String> sortedMap =
//                unsortedMap.entrySet().stream()
//                        .sorted(Entry.comparingByValue())
//                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
//                                (e1, e2) -> e1, LinkedHashMap::new));
//    }
//
//    void maxHash() {
//        Collections.max(seriesMap.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
//    }

    String getLastEpisode(SerieResponse.Serie serieFav) {
        Season.sortSeason(serieFav.seasons);
        for (Season s : serieFav.seasons) {
            for (Episode e : s.episodes) {
                if (!e.visto) {
                    return String.format(Locale.getDefault(), "%02dx%02d - %s", e.seasonNumber, e.episodeNumber, e.name);
                }
            }
        }
        return mContext.getString(R.string.just_watch);
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

            }
        });
    }

    private void setRecycler(@NonNull View view) {
        favAdapter = new FavoritesAdapter(getActivity(), mFavs);
        rvFavs = view.findViewById(R.id.grid_favoritas);
        rvFavs.setHasFixedSize(true);
        rvFavs.setItemViewCacheSize(20);
        rvFavs.setSaveEnabled(true);
        rvFavs.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvFavs.setAdapter(favAdapter);
    }
}
