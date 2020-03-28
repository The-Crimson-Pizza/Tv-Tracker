package com.tracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tracker.R;
import com.tracker.adapters.SeriesAdapter;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.SerieTrendingResponse;

import java.util.ArrayList;

import static com.tracker.util.Constants.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridTrending = null;
    private GridView gridNuevas = null;
    private ArrayList<SerieTrendingResponse.SerieTrending> mTrending = new ArrayList<>();
    private ArrayList<SerieTrendingResponse.SerieTrending> mNuevas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gridTrending = root.findViewById(R.id.gridPopulares);
        gridNuevas = root.findViewById(R.id.gridNuevas);
        final ProgressBar progressBar = root.findViewById(R.id.progressbar);

        SeriesAdapter adapterTrending = new SeriesAdapter(getActivity(), R.layout.lista_series_trending, mTrending);
        SeriesAdapter adapterNew = new SeriesAdapter(getActivity(), R.layout.lista_series_trending, mNuevas);
        new RepositoryAPI().getTrending(mTrending, gridTrending, getActivity(), progressBar, adapterTrending);
        new RepositoryAPI().getNew(mNuevas, gridNuevas, getActivity(), adapterNew);

        gridTrending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, mTrending.get(position).name);
            }
        });

        gridNuevas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, mNuevas.get(position).name);
            }
        });

        return root;
    }
}