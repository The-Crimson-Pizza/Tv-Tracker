package com.tracker.ui;

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
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.series.SerieResponse;

public class SeasonFragment extends Fragment {

    private SerieResponse.Serie mSerie;
    private Context mContext;
    private SeriesViewModel model;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seasons, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

//        TODO -  RECYCLER CON LAS TEMPORADAS Y DENTRO ÚNICO RECYCLER CON TODOS LOS CAPITULOS (NO HACER VARIOS RECYCLER)
        RecyclerView rvSeasons = view.findViewById(R.id.gridCasting);
        rvSeasons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvSeasons.setHasFixedSize(true);
        rvSeasons.setItemViewCacheSize(20);
        rvSeasons.setSaveEnabled(true);

        LiveData<SerieResponse.Serie> s = model.getSerie();
        s.observe(getViewLifecycleOwner(), serie -> {
            if (!serie.credits.cast.isEmpty()) {
//                searchAdapter = new SearchAdapter(mContext, serie);
//                rvSeasons.setAdapter(searchAdapter);
            } else {
                Snackbar.make(view, "Sin datos de reparto", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
            }
        });
    }
}
