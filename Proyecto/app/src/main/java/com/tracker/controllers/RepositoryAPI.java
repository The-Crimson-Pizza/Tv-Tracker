package com.tracker.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.RellenarSerie;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.models.BasicResponse;
import com.tracker.models.VideosResponse;
import com.tracker.models.people.Person;
import com.tracker.models.seasons.Season;
import com.tracker.models.series.Serie;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
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

    private MutableLiveData<List<BasicResponse.SerieBasic>> nuevasData;
    private MutableLiveData<List<BasicResponse.SerieBasic>> popularesData;
    private MutableLiveData<Serie> serieData;


    private static RepositoryAPI repoTMDB;

    public static RepositoryAPI getInstance() {
        if (repoTMDB == null) {
            repoTMDB = new RepositoryAPI();
        }
        return repoTMDB;
    }

    private RepositoryAPI() {
        nuevasData = new MutableLiveData<>();
        popularesData = new MutableLiveData<>();
        serieData = new MutableLiveData<>();
    }

    public DataTMDB getClient() {
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
                        .build();
        return retrofit.create(DataTMDB.class);
    }

    public MutableLiveData<List<BasicResponse.SerieBasic>> getTrending(Context context) {

        if (popularesData == null) {
            popularesData = new MutableLiveData<>();
        }

        getClient().getTrendingSeries().enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> call, @NotNull retrofit2.Response<BasicResponse> response) {
                List<BasicResponse.SerieBasic> listaTrending = null;
                if (response.body() != null) {
                    listaTrending = response.body().trendingSeries;
                }
                if (response.body() != null && !listaTrending.isEmpty()) {
                    popularesData.setValue(listaTrending);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
            }
        });
        return popularesData;
    }

    public MutableLiveData<List<BasicResponse.SerieBasic>> getNew() {

        if (nuevasData == null) {
            nuevasData = new MutableLiveData<>();
        }

        getClient().getNewSeries(2020, ES, POP_DESC).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> call, @NotNull retrofit2.Response<BasicResponse> response) {

                List<BasicResponse.SerieBasic> listaNuevas = null;
                if (response.body() != null) {
                    listaNuevas = response.body().trendingSeries;
                }

                if (response.body() != null && !listaNuevas.isEmpty()) {
                    nuevasData.setValue(listaNuevas);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                nuevasData.setValue(null);
            }
        });
        return nuevasData;

    }


    public MutableLiveData<Serie> getSerie(int idSerie, Context context) {

        getClient().getSerie(idSerie, ES, GET_SERIE_API_EXTRAS).enqueue(new Callback<Serie>() {
            @Override
            public void onResponse(@NotNull Call<Serie> call, @NotNull retrofit2.Response<Serie> response) {
                Serie serie = null;
                if (response.body() != null) {
                    serie = response.body();
                }
                if (response.body() != null && serie != null) {
                    serieData.setValue(serie);
                    getTrailer(serie, context);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Serie> call, @NotNull Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                serieData.setValue(null);
            }
        });
        return serieData;
    }

    private void getTrailer(Serie serie, Context context) {

        getClient().getVideo(serie.id).enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(@NotNull Call<VideosResponse> call, @NotNull retrofit2.Response<VideosResponse> response) {
                List<VideosResponse.Video> videos = null;
                if (response.body() != null) {
                    videos = response.body().results;
                }

                if (response.body() != null && !videos.isEmpty()) {
                    for (VideosResponse.Video v : videos) {
                        if (v.type.equals("Trailer")) {
                            serie.setVideos(v);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<VideosResponse> call, @NotNull Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getSeason(int idSerie, int temporada, final List<Season> listaSerie, Context context) {
        getClient().getSeason(idSerie, temporada, ES).enqueue(new Callback<Season>() {
            @Override
            public void onResponse(@NotNull Call<Season> call, @NotNull retrofit2.Response<Season> response) {
                if (response.body() != null) {
                    listaSerie.addAll(Collections.singleton(response.body()));
                }

                if (response.body() != null && !listaSerie.isEmpty()) {
                    Log.d(TAG, listaSerie.get(0).name);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Season> call, @NotNull Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPerson(int idPerson, final List<Person> actor, Context context) {
        getClient().getPerson(idPerson, ES, GET_PEOPLE_API_EXTRAS).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(@NotNull Call<Person> call, @NotNull retrofit2.Response<Person> response) {
                if (response.body() != null) {
                    actor.addAll(Collections.singleton(response.body()));
                }

                if (response.body() != null && !actor.isEmpty()) {
                    Log.d(TAG, actor.get(0).name);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Person> call, @NotNull Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
            }
        });
    }


}
