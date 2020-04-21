package com.tracker.data;

import com.tracker.models.BasicResponse;
import com.tracker.models.serie.VideosResponse;
import com.tracker.models.actor.PersonResponse;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface with the TMDB API calls
 */
public interface DataTMDB {
    @Headers("Accept: application/json")
    @GET("trending/tv/day")
    Observable<BasicResponse> getTrendingSeries();

    @Headers("Accept: application/json")
    @GET("discover/tv")
    Observable<BasicResponse> getNewSeries(@Query("first_air_date_year") int year,
                                           @Query("language") String language,
                                           @Query("sort_by") String sort);

    @Headers("Accept: application/json")
    @GET("tv/{id_serie}")
    Observable<SerieResponse.Serie> getSerie(@Path("id_serie") int id,
                                             @Query("language") String language,
                                             @Query("append_to_response") String append);

    @Headers("Accept: application/json")
    @GET("tv/{tv_id}/videos")
    Observable<VideosResponse> getTrailer(@Path("tv_id") int idSerie);

    @Headers("Accept: application/json")
    @GET("tv/{id_serie}/season/{season_number}")
    Observable<Season> getSeason(@Path("id_serie") int id,
                                 @Path("season_number") int season,
                                 @Query("language") String language);

    @Headers("Accept: application/json")
    @GET("person/{person_id}")
    Observable<PersonResponse.Person> getPerson(@Path("person_id") int idPersona,
                                                @Query("language") String language,
                                                @Query("append_to_response") String append);

    @Headers("Accept: application/json")
    @GET("search/person")
    Observable<PersonResponse> searchPerson(@Query("query") String query,
                                            @Query("language") String language);

    @Headers("Accept: application/json")
    @GET("search/tv")
    Observable<SerieResponse> searchSerie(@Query("query") String query,
                                          @Query("language") String language);

    @Headers("Accept: application/json")
    @GET("discover/tv")
    Observable<BasicResponse> getSeriesByGenre(@Query("with_genres") int idGenre,
                                               @Query("language") String language,
                                               @Query("timezone") String zone,
                                               @Query("sort_by") String sort);

    @Headers("Accept: application/json")
    @GET("discover/tv")
    Observable<BasicResponse> getSeriesByNetwork(@Query("with_networks") int idNetwork,
                                                 @Query("language") String language,
                                                 @Query("timezone") String zone,
                                                 @Query("sort_by") String sort);

}
