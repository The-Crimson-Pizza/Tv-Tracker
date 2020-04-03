package com.tracker.adapters;

import android.content.Context;

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
    private MutableLiveData<Serie> mSerie;


    public LiveData<List<BasicResponse.SerieBasic>> getNuevas(Context context) {
        if (mNuevas == null) {
            mNuevas = RepositoryAPI.getInstance().getNew();
        }
        return mNuevas;
    }

    public LiveData<List<BasicResponse.SerieBasic>> getPopulares(Context context) {
        if (mPopulares == null) {
            mPopulares = RepositoryAPI.getInstance().getTrending(context);
        }
        return mPopulares;
    }

    public LiveData<Serie> getSerie(int idSerie, Context context) {
        mSerie = RepositoryAPI.getInstance().getSerie(idSerie, context);
        return mSerie;
    }

    public LiveData<Serie> getCurrentSerie() {
        return this.mSerie;
    }

}
