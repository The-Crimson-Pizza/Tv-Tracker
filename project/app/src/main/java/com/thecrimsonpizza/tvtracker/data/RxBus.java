package com.thecrimsonpizza.tvtracker.data;

import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Class that sends the Serie data from {@link com.thecrimsonpizza.tvtracker.ui.series.SerieFragment} to {@link com.thecrimsonpizza.tvtracker.ui.series.SinopsisFragment}
 */
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

    private final PublishSubject<SerieResponse.Serie> publisher = PublishSubject.create();

    public void publish(SerieResponse.Serie event) {
        publisher.onNext(event);
    }

    public Observable<SerieResponse.Serie> listen() {
        return publisher;
    }

}
