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

import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;
import com.tracker.adapters.EpisodeAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Episode;
import com.tracker.models.serie.SerieResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE;
import static com.tracker.util.Constants.ID_SEASON;

public class EpisodesFragment extends Fragment {

    private int mNumTemporada;
    private RecyclerView rvEpisodes;
    private Context mContext;
    private EpisodeAdapter adapter;
    private SerieResponse.Serie mSerie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNumTemporada = getArguments().getInt(ID_SEASON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_episodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvEpisodes = view.findViewById(R.id.gridEpisodes);
        rvEpisodes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvEpisodes.setHasFixedSize(true);
        rvEpisodes.setItemViewCacheSize(20);
        rvEpisodes.setSaveEnabled(true);

        LiveData<SerieResponse.Serie> serieLiveData = model.getSerie();
        serieLiveData.observe(getViewLifecycleOwner(), serie -> {
            mSerie = serie;
            if (!mSerie.seasons.get(mNumTemporada).episodes.isEmpty()) {
                int runtime = 0;
                if (!mSerie.episodeRunTime.isEmpty()) {
                    runtime = mSerie.episodeRunTime.get(0);
                }
                adapter = new EpisodeAdapter(mContext, mSerie.seasons.get(mNumTemporada).episodes, runtime);
                rvEpisodes.setAdapter(adapter);
            } else {
                adapter = new EpisodeAdapter(mContext, null, mSerie.episodeRunTime.get(0));
                rvEpisodes.setAdapter(adapter);
                Snackbar.make(view, R.string.no_data, LENGTH_INDEFINITE).show();
            }

//            getEpisodes(view);
        });

    }
}
