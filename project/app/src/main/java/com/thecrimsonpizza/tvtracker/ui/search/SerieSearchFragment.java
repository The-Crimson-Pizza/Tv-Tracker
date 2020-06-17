package com.thecrimsonpizza.tvtracker.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.SearchAdapter;
import com.thecrimsonpizza.tvtracker.data.SeriesViewModel;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SerieSearchFragment extends Fragment {

    private final List<SerieResponse.Serie> mListaSeries = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Context mContext;
    private RecyclerView rvSeries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_serie_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewSwitcher switcherSerie = view.findViewById(R.id.switcherSearchSerie);
        SeriesViewModel searchViewModel = new ViewModelProvider(requireActivity()).get(SeriesViewModel.class);
        searchAdapter = new SearchAdapter(mContext, mListaSeries, null, true);
        rvSeries = view.findViewById(R.id.rvSeries);

        setRecycler();

        searchViewModel.setQuery(getString(R.string.empty));
        LiveData<String> queryResult = searchViewModel.getQuery();
        queryResult.observe(getViewLifecycleOwner(), query -> {
            if (query.isEmpty()) {
                if (R.id.search_image == switcherSerie.getNextView().getId())
                    switcherSerie.showNext();
            } else {
                if (R.id.rvSeries == switcherSerie.getNextView().getId())
                    switcherSerie.showNext();
                getResults(query);
            }
        });
    }

    private void setRecycler() {
        rvSeries.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvSeries.setHasFixedSize(true);
        rvSeries.setItemViewCacheSize(20);
        rvSeries.setSaveEnabled(true);
        rvSeries.setAdapter(searchAdapter);
    }

    private void getResults(String query) {
        TmdbRepository.getInstance().searchSerie(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerieResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mListaSeries.clear();
                        searchAdapter.notifyDataSetChanged();
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
