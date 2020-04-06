package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.RellenarSerie;
import com.tracker.adapters.SeriesViewModel;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.models.BasicResponse;
import com.tracker.models.series.Serie;

import java.util.List;

public class HomeFragment extends Fragment {

    private List<BasicResponse.SerieBasic> mPopulares;
    private List<BasicResponse.SerieBasic> mNuevas;
    private SeriesBasicAdapter adapterPopular;
    private SeriesBasicAdapter adapterNueva;
    RecyclerView rvPopulares;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);

        rvPopulares.setHasFixedSize(true);
        rvNuevas.setHasFixedSize(true);
        rvPopulares.setItemViewCacheSize(20);
        rvNuevas.setItemViewCacheSize(20);
        rvPopulares.setSaveEnabled(true);
        rvPopulares.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvNuevas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        LiveData<List<BasicResponse.SerieBasic>> s = model.getPopulares();
        s.observe(getViewLifecycleOwner(), series -> {
            mPopulares = series;
            adapterPopular = new SeriesBasicAdapter(getActivity(), mPopulares);
            rvPopulares.setAdapter(adapterPopular);
        });

        LiveData<List<BasicResponse.SerieBasic>> n = model.getNuevas();
        n.observe(getViewLifecycleOwner(), series -> {
            mNuevas = series;
            adapterNueva = new SeriesBasicAdapter(getActivity(), mNuevas);
            rvNuevas.setAdapter(adapterNueva);
        });
    }
}