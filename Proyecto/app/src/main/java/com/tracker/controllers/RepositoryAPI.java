package com.tracker.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.models.SerieBasicResponse;
import com.tracker.models.people.Person;
import com.tracker.models.seasons.TvSeason;
import com.tracker.models.series.Serie;

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
import static com.tracker.util.Constants.TAG;

public class RepositoryAPI {

    private DataTMDB getClient() {
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

    public void getTrending(final List<SerieBasicResponse.SerieBasic> listaTrending,
                            RecyclerView rvTrending, Context context) {

        getClient().getTrendingSeries().enqueue(new Callback<SerieBasicResponse>() {
            @Override
            public void onResponse(Call<SerieBasicResponse> call, retrofit2.Response<SerieBasicResponse> response) {
                if (response.body() != null) {
                    listaTrending.addAll(response.body().trendingSeries);
                }

                if (response.body() != null && !listaTrending.isEmpty()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    rvTrending.setLayoutManager(layoutManager);
                    SeriesBasicAdapter adapterTrending = new SeriesBasicAdapter(context, listaTrending);
                    rvTrending.setAdapter(adapterTrending);
                    adapterTrending.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<SerieBasicResponse> call, Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e("TAG", t.getMessage());
            }
        });
    }

    public void getNew(final List<SerieBasicResponse.SerieBasic> listaNuevas,
                       RecyclerView recyclerView, Context context) {

        getClient().getNewSeries(2020, ES, "populariry.desc").enqueue(new Callback<SerieBasicResponse>() {
            @Override
            public void onResponse(Call<SerieBasicResponse> call, retrofit2.Response<SerieBasicResponse> response) {
                if (response.body() != null) {
                    listaNuevas.addAll(response.body().trendingSeries);
                }

                if (response.body() != null && !listaNuevas.isEmpty()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    SeriesBasicAdapter adapterNuevo = new SeriesBasicAdapter(context, listaNuevas);
                    recyclerView.setAdapter(adapterNuevo);
                    adapterNuevo.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SerieBasicResponse> call, Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e("TAG", t.getMessage());
            }
        });
    }

    public void getSerie(int idSerie, final List<Serie> listaSerie, Context context) {
        getClient().getSerie(idSerie, ES, "credits,similar,external_ids,videos,images").enqueue(new Callback<Serie>() {
            @Override
            public void onResponse(Call<Serie> call, retrofit2.Response<Serie> response) {
                if (response.body() != null) {
                    listaSerie.addAll(Collections.singleton(response.body()));
                }

                if (response.body() != null && !listaSerie.isEmpty()) {
                    Log.d(TAG, listaSerie.get(0).name);
                    Log.d(TAG, listaSerie.get(0).credits.cast.get(0).character);
                }
            }

            @Override
            public void onFailure(Call<Serie> call, Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getSeason(int idSerie, int temporada, final List<TvSeason> listaSerie, Context context) {
        getClient().getSeason(idSerie, temporada, ES).enqueue(new Callback<TvSeason>() {
            @Override
            public void onResponse(Call<TvSeason> call, retrofit2.Response<TvSeason> response) {
                if (response.body() != null) {
                    listaSerie.addAll(Collections.singleton(response.body()));
                }

                if (response.body() != null && !listaSerie.isEmpty()) {
                    Log.d(TAG, listaSerie.get(0).name);
                }
            }

            @Override
            public void onFailure(Call<TvSeason> call, Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getPerson(int idPerson, final List<Person> actor, Context context) {
        getClient().getPerson(idPerson, ES, "tv_credits,external_ids").enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, retrofit2.Response<Person> response) {
                if (response.body() != null) {
                    actor.addAll(Collections.singleton(response.body()));
                }

                if (response.body() != null && !actor.isEmpty()) {
                    Log.d(TAG, actor.get(0).name);
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
