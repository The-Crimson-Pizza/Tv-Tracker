package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.BasicResponse;
import com.tracker.models.HomeViewModel;
import com.tracker.models.SerieViewModel;
import com.tracker.models.series.Serie;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel model;

    private ArrayList<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private List<BasicResponse.SerieBasic> mNuevas;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);

        RepositoryAPI.getInstance().getTrending(mPopulares, rvPopulares, getActivity());
        RepositoryAPI.getInstance().getNew(rvNuevas, getActivity());

        model = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        LiveData<List<BasicResponse.SerieBasic>> s = model.getNuevas(rvNuevas, getActivity());

        s.observe(getViewLifecycleOwner(), seriesN -> mNuevas = seriesN);
    }
}