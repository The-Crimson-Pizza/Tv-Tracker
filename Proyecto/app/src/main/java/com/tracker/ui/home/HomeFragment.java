package com.tracker.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.DescripcionSerie;
import com.tracker.R;
import com.tracker.adapters.SeriesTrendingAdapter;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.SerieTrendingResponse;

import java.util.ArrayList;

import static com.tracker.util.Constants.ID_SERIE;
import static com.tracker.util.Constants.TAG;

public class HomeFragment extends Fragment implements SeriesTrendingAdapter.OnTrendingListener {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView = null;
    private ArrayList<SerieTrendingResponse.SerieTrending> mTrending = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        final ProgressBar progressBar = root.findViewById(R.id.progressbar);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        new RepositoryAPI().getTrending(this, mTrending, recyclerView, getActivity(), progressBar);

        return root;
    }

    @Override
    public void onClickTrending(int position) {
        Log.d(TAG, mTrending.get(position).name);
        Intent intent = new Intent(getActivity(), DescripcionSerie.class);
        intent.putExtra(ID_SERIE, mTrending.get(position).id);
        startActivity(intent);
    }
}