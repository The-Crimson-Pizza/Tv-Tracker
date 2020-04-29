package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.EpisodeAdapter;
import com.tracker.data.FirebaseDb;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Episode;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class EpisodesFragment extends Fragment {

    private int mPosTemporada;
    private RecyclerView rvEpisodes;
    private Context mContext;
    private EpisodeAdapter mEpisodeAdapter;
    private SerieResponse.Serie mSerie;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private List<Episode> mEpisodes = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosTemporada = getArguments().getInt(Constants.ID_SEASON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_episodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecycler(view);

        SeriesViewModel model = new ViewModelProvider(requireActivity()).get(SeriesViewModel.class);
        LiveData<SerieResponse.Serie> serieLiveData = model.getSerie();
        serieLiveData.observe(getViewLifecycleOwner(), serie -> {
            mSerie = serie;
            setAdapters(mSerie);
        });

        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                List<SerieResponse.Serie> favTemp = dataSnapshot.getValue(new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                });
                if (favTemp != null) {
                    mFavs.addAll(favTemp);
                    if (mSerie != null) {
                        mSerie.checkFav(mFavs);
                        mEpisodes.clear();
                        mEpisodes.addAll(mSerie.seasons.get(mPosTemporada).episodes);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nada de momento
            }
        });
    }

    private void setAdapters(SerieResponse.Serie serie) {
        if (!serie.seasons.get(mPosTemporada).episodes.isEmpty()) {
            mEpisodes.clear();
            mEpisodes.addAll(serie.seasons.get(mPosTemporada).episodes);
            mEpisodeAdapter = new EpisodeAdapter(mContext, mEpisodes, serie, mFavs, mPosTemporada);
            rvEpisodes.setAdapter(mEpisodeAdapter);
        }
    }

    private void setRecycler(@NonNull View view) {
        rvEpisodes = view.findViewById(R.id.gridEpisodes);
        rvEpisodes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvEpisodes.setHasFixedSize(true);
        rvEpisodes.setItemViewCacheSize(20);
        rvEpisodes.setSaveEnabled(true);
    }
}
