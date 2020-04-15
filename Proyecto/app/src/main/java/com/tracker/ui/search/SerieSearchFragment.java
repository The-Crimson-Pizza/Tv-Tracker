package com.tracker.ui.search;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SerieSearchFragment extends Fragment {

    private SeriesViewModel model;
    private List<SerieResponse.Serie> mListaSeries = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Context mContext;
    private RecyclerView rvSeries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_serie_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvSeries = view.findViewById(R.id.rvSeries);
        rvSeries.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvSeries.setHasFixedSize(true);
        rvSeries.setItemViewCacheSize(20);
        rvSeries.setSaveEnabled(true);
        searchAdapter = new SearchAdapter(mContext, mListaSeries, null, true);
        rvSeries.setAdapter(searchAdapter);

        LiveData<String> query = model.getQuery();
        query.observe(getViewLifecycleOwner(), this::getResults);
    }

    private void getResults(String query) {
        RepositoryAPI.getInstance().searchSerie(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerieResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mListaSeries.clear();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull SerieResponse serieResponse) {
                        mListaSeries.clear();
                        mListaSeries.addAll(serieResponse.results);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mListaSeries.clear();
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }
}
