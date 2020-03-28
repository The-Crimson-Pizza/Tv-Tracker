package com.tracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.SerieBasicResponse;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<SerieBasicResponse.SerieBasic> mTrending = new ArrayList<>();
    private ArrayList<SerieBasicResponse.SerieBasic> mNuevas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvTrending = root.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = root.findViewById(R.id.gridNuevas);

        RepositoryAPI seriesMain = new RepositoryAPI();
        seriesMain.getTrending(mTrending, rvTrending, getActivity());
        seriesMain.getNew(mNuevas, rvNuevas, getActivity());

        return root;
    }
}