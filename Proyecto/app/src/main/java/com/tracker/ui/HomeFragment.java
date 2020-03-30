package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.SerieBasicResponse;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<SerieBasicResponse.SerieBasic> mTrending = new ArrayList<>();
    private ArrayList<SerieBasicResponse.SerieBasic> mNuevas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvTrending = root.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = root.findViewById(R.id.gridNuevas);

        FragmentManager fManager = getParentFragmentManager();

        RepositoryAPI seriesMain = new RepositoryAPI();

//        SeriesBasicAdapter adapterTrending = new SeriesBasicAdapter();
        seriesMain.getTrending(mTrending, rvTrending, getActivity(), fManager);
        seriesMain.getNew(mNuevas, rvNuevas, getActivity(), fManager);


        return root;
    }
}