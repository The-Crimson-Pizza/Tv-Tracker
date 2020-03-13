package com.tracker.controllers;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.tracker.adapters.SeriesTrendingAdapter;
import com.tracker.models.DataTMDB;
import com.tracker.models.SerieTrendingResponse;
import com.tracker.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

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

import static com.tracker.util.Constants.BASE_URL;

public class RepositorioAPI {

    ArrayList<SerieTrendingResponse.SerieTrending> mTrending;

    public DataTMDB getClient() {
        // "18f61adb80d286bb036df43e60d7aae6"
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", "18f61adb80d286bb036df43e60d7aae6").build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
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

    public void getTrending(RecyclerView rv, Context ct) {
        getClient().getTrending().enqueue(new Callback<SerieTrendingResponse>() {
            @Override
            public void onResponse(Call<SerieTrendingResponse> call, retrofit2.Response<SerieTrendingResponse> response) {
//                Log.d("TAG", String.valueOf(response.code()));
                mTrending = response.body().trendingSeries;

                if (response.body() != null && !mTrending.isEmpty()) {
                    SeriesTrendingAdapter sta = new SeriesTrendingAdapter(ct, mTrending);
                    rv.setAdapter(sta);
                    sta.notifyDataSetChanged();
//                    mAdapter = new MyAdapter(ConsulatePlaces.this, R.layout.list_places, mPlaces);
//                    lv.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SerieTrendingResponse> call, Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });
    }
}
