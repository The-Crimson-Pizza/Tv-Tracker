package com.tracker.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.models.serie.SerieResponse;

/**
 * Class that sends the Serie data from {@link com.tracker.ui.series.SerieFragment} to {@link com.tracker.ui.series.CastFragment}
 * and the Query data to {@link com.tracker.ui.search.ActorSearchFragment} and {@link com.tracker.ui.search.SerieSearchFragment}
 */
public class SeriesViewModel extends ViewModel {

    private MutableLiveData<SerieResponse.Serie> mSerie = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();

    public LiveData<SerieResponse.Serie> getSerie() {
        return mSerie;
    }

    public void setSerie(SerieResponse.Serie serie) {
        mSerie.setValue(serie);
    }

    public void setQuery(String value) {
        mQuery.setValue(value);
    }

    public LiveData<String> getQuery() {
        return mQuery;
    }

}
