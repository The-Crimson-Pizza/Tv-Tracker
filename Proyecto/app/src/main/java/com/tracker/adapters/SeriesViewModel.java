package com.tracker.adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.BasicResponse;
import com.tracker.models.series.Serie;

import java.util.List;

public class SeriesViewModel extends ViewModel {

    private MutableLiveData<List<BasicResponse.SerieBasic>> mNuevas;
    private MutableLiveData<List<BasicResponse.SerieBasic>> mPopulares;
    private MutableLiveData<Serie> mSerie = new MutableLiveData<>();
    ;


    public LiveData<List<BasicResponse.SerieBasic>> getNuevas() {
        if (mNuevas == null) {
            mNuevas = RepositoryAPI.getInstance().getNew();
        }
        return mNuevas;
    }

    public LiveData<List<BasicResponse.SerieBasic>> getPopulares() {
        if (mPopulares == null) {
            mPopulares = RepositoryAPI.getInstance().getTrending();
        }
        return mPopulares;
    }

    public LiveData<Serie> getSerie() {
        return mSerie;
    }

    public void init(Serie serie) {
        mSerie.setValue(serie);
    }
}
