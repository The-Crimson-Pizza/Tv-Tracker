package com.tracker.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SeriesTrendingAdapter;
import com.tracker.models.DataTMDB;
import com.tracker.models.SerieTrendingResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tracker.util.Constants.API_KEY;
import static com.tracker.util.Constants.API_KEY_STRING;
import static com.tracker.util.Constants.BASE_URL;

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

    public void getTrending(SeriesTrendingAdapter.OnTrendingListener listener, final List<SerieTrendingResponse.SerieTrending> listaTrending,
                            RecyclerView recyclerView, Context context,  ProgressBar progressBar) {
        getClient().getTrending().enqueue(new Callback<SerieTrendingResponse>() {
            @Override
            public void onResponse(Call<SerieTrendingResponse> call, retrofit2.Response<SerieTrendingResponse> response) {
                if (response.body() != null) {
                    listaTrending.addAll(response.body().trendingSeries);
                }

                if (response.body() != null && !listaTrending.isEmpty()) {
                    recyclerView.setAdapter(new SeriesTrendingAdapter(context, listaTrending,listener));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<SerieTrendingResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, R.string.no_conn, Toast.LENGTH_LONG).show();
                Log.e("TAG", t.getMessage());
            }
        });
    }
}
