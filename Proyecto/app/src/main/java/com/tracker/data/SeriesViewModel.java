package com.tracker.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.models.SerieFav;
import com.tracker.models.serie.SerieResponse;

import java.util.List;

public class SeriesViewModel extends ViewModel {

    private MutableLiveData<SerieResponse.Serie> mSerie = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();
    private MutableLiveData<List<SerieFav>> mFavs = new MutableLiveData<>();

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

    public void setFavs(List<SerieFav> favs) {
        mFavs.setValue(favs);
    }

    public LiveData<List<SerieFav>> getFavs() {
        return mFavs;
    }


}
