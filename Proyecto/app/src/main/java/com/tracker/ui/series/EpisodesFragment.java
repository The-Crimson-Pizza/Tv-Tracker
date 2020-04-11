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

import com.tracker.R;
import com.tracker.adapters.EpisodeAdapter;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Season;

import java.util.List;

import static com.tracker.util.Constants.ID_SEASON;

public class EpisodesFragment extends Fragment {

    private SeriesViewModel model;
    private int mPosition;
    private RecyclerView rvCapitulos;
    private Context mContext;
    private EpisodeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ID_SEASON);
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

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvCapitulos = view.findViewById(R.id.gridEpisodes);
        rvCapitulos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCapitulos.setHasFixedSize(true);
        rvCapitulos.setItemViewCacheSize(20);
        rvCapitulos.setSaveEnabled(true);

        LiveData<List<Season>> s = model.getSeasons();
        s.observe(getViewLifecycleOwner(), seasons -> {
            adapter = new EpisodeAdapter(mContext, seasons.get(mPosition).episodes);
            rvCapitulos.setAdapter(adapter);
        });
    }
}
