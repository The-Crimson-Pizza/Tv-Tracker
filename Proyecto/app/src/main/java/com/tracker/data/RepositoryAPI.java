package com.tracker.data;

import android.util.Log;

import com.tracker.models.BasicResponse;
import com.tracker.models.VideosResponse;
import com.tracker.models.people.PersonResponse;
import com.tracker.models.seasons.Season;
import com.tracker.models.series.SerieResponse;
import com.tracker.util.Util;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tracker.util.Constants.API_KEY;
import static com.tracker.util.Constants.API_KEY_STRING;
import static com.tracker.util.Constants.BASE_URL;
import static com.tracker.util.Constants.ES;
import static com.tracker.util.Constants.GET_PEOPLE_API_EXTRAS;
import static com.tracker.util.Constants.GET_SERIE_API_EXTRAS;
import static com.tracker.util.Constants.POP_DESC;
import static com.tracker.util.Constants.TAG;

public class RepositoryAPI {

    private static RepositoryAPI repoTMDB;
    private String language;

    public static RepositoryAPI getInstance() {
        if (repoTMDB == null) {
            repoTMDB = new RepositoryAPI();
        }
        return repoTMDB;
    }

    private RepositoryAPI() {
        language = new Util().getLanguage().replace("_", "-");
    }

    private DataTMDB getRetrofitService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_STRING, API_KEY).build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .build();
        return retrofit.create(DataTMDB.class);
    }


    public Observable<BasicResponse> getTrending() {
        return getRetrofitService().getTrendingSeries();
    }

    public Observable<BasicResponse> getNew() {
        return getRetrofitService().getNewSeries(2020, language, POP_DESC);
    }

    public Observable<SerieResponse.Serie> getSerie(int idSerie) {
        Observable<SerieResponse.Serie> obsSerie = getRetrofitService().getSerie(idSerie, language, GET_SERIE_API_EXTRAS);
        Observable<VideosResponse> obsVideo = getRetrofitService().getTrailer(idSerie);
        return Observable.zip(obsSerie, obsVideo, (serie, videosResponse) -> {
            List<VideosResponse.Video> trailers = videosResponse.results;
            for (VideosResponse.Video v : trailers) {
                if (v.type.equals("Trailer")) {
                    serie.setVideos(v);
                    break;
                }
            }
            return serie;
        });
    }

    public Observable<Season> getSeason(int idSerie, int temporada) {
        return getRetrofitService().getSeason(idSerie, temporada, language);
    }

    public Observable<PersonResponse.Person> getPerson(int idPerson) {
        return getRetrofitService().getPerson(idPerson, language, GET_PEOPLE_API_EXTRAS);
    }

    public Observable<PersonResponse> searchPerson(String query) {
        return getRetrofitService().searchPerson(query, language);
    }

    public Observable<SerieResponse> searchSerie(String query) {
        return getRetrofitService().searchSerie(query, language);
    }


}
