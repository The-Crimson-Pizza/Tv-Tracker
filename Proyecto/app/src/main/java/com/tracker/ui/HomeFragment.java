package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.BasicResponse;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private ArrayList<BasicResponse.SerieBasic> mNuevas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);

        RepositoryAPI seriesMain = new RepositoryAPI();

//        SeriesBasicAdapter adapterTrending = new SeriesBasicAdapter();
        seriesMain.getTrending(mPopulares, rvPopulares, getActivity());
        seriesMain.getNew(mNuevas, rvNuevas, getActivity());

    }
}