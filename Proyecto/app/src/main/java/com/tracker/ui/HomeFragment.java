package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.models.BasicResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class HomeFragment extends Fragment {

    private List<BasicResponse.SerieBasic> mPopulares;
    private List<BasicResponse.SerieBasic> mNuevas;
    private SeriesBasicAdapter adapterPopular;
    private SeriesBasicAdapter adapterNueva;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);

        rvPopulares.setHasFixedSize(true);
        rvNuevas.setHasFixedSize(true);
        rvPopulares.setItemViewCacheSize(20);
        rvNuevas.setItemViewCacheSize(20);
        rvPopulares.setSaveEnabled(true);
        rvPopulares.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvNuevas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        RepositoryAPI.getInstance().getTrending()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> {
                    mPopulares = series.trendingSeries;
                    adapterPopular = new SeriesBasicAdapter(getActivity(), mPopulares);
                    rvPopulares.setAdapter(adapterPopular);
                });

        RepositoryAPI.getInstance().getNew()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> {
                    mNuevas = series.trendingSeries;
                    adapterNueva = new SeriesBasicAdapter(getActivity(), mNuevas);
                    rvNuevas.setAdapter(adapterNueva);
                });
    }
}