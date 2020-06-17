package com.thecrimsonpizza.tvtracker.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

/**
 * Class that sends the Serie data from {@link com.thecrimsonpizza.tvtracker.ui.series.SerieFragment} to {@link com.thecrimsonpizza.tvtracker.ui.series.CastFragment}
 * and the Query data to {@link com.thecrimsonpizza.tvtracker.ui.search.ActorSearchFragment} and {@link com.thecrimsonpizza.tvtracker.ui.search.SerieSearchFragment}
 */
public class SeriesViewModel extends ViewModel {

    private final MutableLiveData<SerieResponse.Serie> mSerie = new MutableLiveData<>();
    private final MutableLiveData<String> mQuery = new MutableLiveData<>();

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
