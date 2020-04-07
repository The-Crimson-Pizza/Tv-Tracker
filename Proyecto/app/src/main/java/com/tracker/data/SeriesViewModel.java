package com.tracker.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.models.series.Serie;

public class SeriesViewModel extends ViewModel {

    private MutableLiveData<Serie> mSerie = new MutableLiveData<>();

    public LiveData<Serie> getSerie() {
        return mSerie;
    }

    public void init(Serie serie) {
        mSerie.setValue(serie);
    }
}
