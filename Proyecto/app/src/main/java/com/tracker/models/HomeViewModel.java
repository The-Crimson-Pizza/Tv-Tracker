package com.tracker.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.controllers.RepositoryAPI;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<BasicResponse.SerieBasic>> nuevas;
    private MutableLiveData<List<BasicResponse.SerieBasic>> populares;
    private RepositoryAPI repoTMDB;

    public LiveData<List<BasicResponse.SerieBasic>> getNuevas(RecyclerView recycler, Context context) {
        return RepositoryAPI.getInstance().getNew(recycler, context);
    }

    public LiveData<List<BasicResponse.SerieBasic>> getPopulares(RecyclerView recycler, Context context) {
        return RepositoryAPI.getInstance().getTrending(recycler, context);
    }

    public LiveData<List<BasicResponse.SerieBasic>> getCurrentNuevas() {
        return nuevas;
    }

    public LiveData<List<BasicResponse.SerieBasic>> getCurrentPopulares() {
        return populares;
    }

}
