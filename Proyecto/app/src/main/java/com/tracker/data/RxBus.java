package com.tracker.data;

import com.tracker.models.series.SerieResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class RxBus {

    private static RxBus mInstance;

    public static RxBus getInstance() {
        if (mInstance == null) {
            mInstance = new RxBus();
        }
        return mInstance;
    }

    private RxBus() {
    }

    private PublishSubject<SerieResponse.Serie> publisher = PublishSubject.create();

    public void publish(SerieResponse.Serie event) {
        publisher.onNext(event);
    }

    public Observable<SerieResponse.Serie> listen() {
        return publisher;
    }

}
