package com.tracker.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DataTMDB {
    @Headers("Accept: application/json")
    @GET("trending/tv/day")
    Call<SerieTrendingResponse> getTrendingSeries();

    @Headers("Accept: application/json")
    @GET("discover/tv")
    Call<SerieTrendingResponse> getNewSeries(@Query("first_air_date_year") int year,
                                             @Query("language") String language,
                                             @Query("sort_by") String sort);


}
