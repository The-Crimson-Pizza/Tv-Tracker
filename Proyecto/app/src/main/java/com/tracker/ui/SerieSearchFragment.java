package com.tracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.tracker.R;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.people.PersonResponse;
import com.tracker.models.series.SerieResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.tracker.util.Constants.TAG;

public class SerieSearchFragment extends Fragment {

    private SeriesViewModel model;
    private List<SerieResponse.Serie> mListaSeries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        return inflater.inflate(R.layout.fragment_serie_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = view.findViewById(R.id.prueba);
        LiveData<String> query = model.getQuery();
        query.observe(getViewLifecycleOwner(), q -> {
            if (q.length() > 2) {
//                getResults(q);
            }
            tv.setText(q);
        });
    }

    private void getResults(String query) {
//        RepositoryAPI.getInstance().searchSerie(query)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(lista -> {
//                    mListaSeries = lista.results;
//                    if (mListaSeries.size() > 0) {
//                        Log.d(TAG, mListaSeries.get(0).name);
//                    }
//                    // todo - rellenar recycler
//                });

        RepositoryAPI.getInstance().searchSerie(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerieResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull SerieResponse serieResponse) {
                        mListaSeries = serieResponse.results;
                        if (mListaSeries.size() > 0) {
                            Log.d(TAG, mListaSeries.get(0).name);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
