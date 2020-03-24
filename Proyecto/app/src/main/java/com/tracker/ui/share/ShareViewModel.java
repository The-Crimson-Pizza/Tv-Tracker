package com.tracker.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tracker.models.SerieTrendingResponse;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    MutableLiveData<SerieTrendingResponse.SerieTrending> serie;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getTextShare() {
        return mText;
    }
}