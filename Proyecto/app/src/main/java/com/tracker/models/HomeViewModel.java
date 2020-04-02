package com.tracker.models;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.series.Serie;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<BasicResponse.SerieBasic>> nuevas;
    private MutableLiveData<List<BasicResponse.SerieBasic>> populares;
    private RepositoryAPI repoTMDB;

    public LiveData<List<BasicResponse.SerieBasic>> getNuevas(RecyclerView recycler, Context context) {
        return RepositoryAPI.getInstance().getNew(recycler,context);
    }

//    public LiveData<ArrayList<BasicResponse.SerieBasic>> getPopulares(View vista, int idSerie, Context context) {
//        return RepositoryAPI.getInstance().getTrending();
//    }

    public LiveData<List<BasicResponse.SerieBasic>> getCurrentNuevas() {
        return nuevas;
    }

//    public LiveData<ArrayList<BasicResponse.SerieBasic>> getCurrentPopulares() {
//        return populares;
//    }

}
