package com.tracker.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.models.seasons.Season;
import com.tracker.models.series.SerieResponse;

import java.util.List;

public class SeriesViewModel extends ViewModel {

    private MutableLiveData<SerieResponse.Serie> mSerie = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();
    private MutableLiveData<List<Season>>  mSeasons = new MutableLiveData<>();

    public LiveData<SerieResponse.Serie> getSerie() {
        return mSerie;
    }

    public void init(SerieResponse.Serie serie) {
        mSerie.setValue(serie);
    }

    public void setQuery(String value) {
        mQuery.setValue(value);
    }

    public LiveData<String> getQuery() {
        return mQuery;
    }

    public void setSeasons(List<Season> seasons){
        mSeasons.setValue(seasons);
    }

    public LiveData<List<Season>> getSeasons(){
        return mSeasons;
    }
}
