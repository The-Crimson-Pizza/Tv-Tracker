package com.tracker.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DataTMDB {
    @Headers("Accept: application/json")
    @GET("trending/tv/day")
    Call<SerieTrendingResponse> getTrending();


}
