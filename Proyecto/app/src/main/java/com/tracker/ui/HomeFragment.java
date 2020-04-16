package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.HomeAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.BasicResponse;
import com.tracker.models.SerieFav;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.URL_FAV;

public class HomeFragment extends Fragment {

    private List<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private List<BasicResponse.SerieBasic> mNuevas = new ArrayList<>();
    private SeriesViewModel model;
    private Context mContext;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        HomeAdapter adapterPopular = new HomeAdapter(getActivity(), mPopulares);
        HomeAdapter adapterNueva = new HomeAdapter(getActivity(), mNuevas);

        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);

        initRecycler(rvNuevas, adapterNueva);
        initRecycler(rvPopulares, adapterPopular);

        RepositoryAPI.getInstance().getTrendingSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> refreshData(mPopulares, adapterPopular, series.basicSeries));

        RepositoryAPI.getInstance().getNewSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> refreshData(mNuevas, adapterNueva, series.basicSeries));

        SerieFav.readFav(mContext.getFilesDir() + URL_FAV, model);
    }

    private void refreshData(List<BasicResponse.SerieBasic> lista, HomeAdapter adapter, List<BasicResponse.SerieBasic> response) {
        lista.clear();
        lista.addAll(response);
        adapter.notifyDataSetChanged();
    }

    private void initRecycler(RecyclerView rv, HomeAdapter adapter) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter);
    }
}