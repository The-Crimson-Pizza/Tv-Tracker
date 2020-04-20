
package com.tracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracker.models.BasicResponse;
import com.tracker.models.seasons.Season;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SerieResponse {

    @SerializedName("results")
    @Expose
    public final List<Serie> results = null;

    public SerieResponse() {

    }

    public static class Serie implements Serializable {

        public int id;
        public String name;
        @SerializedName("original_name")
        public String originalName;
        @SerializedName("first_air_date")
        public String firstAirDate;
        @SerializedName("last_air_date")
        public String lastAirDate;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("backdrop_path")
        public String backdropPath;
        public String status;
        @SerializedName("episode_run_time")
        public List<Integer> episodeRunTime = null;
        public String homepage;
        @SerializedName("in_production")
        public boolean inProduction;
        @SerializedName("number_of_episodes")
        public int numberOfEpisodes;
        @SerializedName("number_of_seasons")
        public int numberOfSeasons;
        @SerializedName("origin_country")
        public List<String> originCountry = null;
        @SerializedName("original_language")
        public String originalLanguage;
        public String overview;
        @SerializedName("vote_average")
        public float voteAverage;
        public List<Genre> genres = null;
        public List<Network> networks = null;
        public Credits credits;
        public Similar similar;
        @SerializedName("external_ids")
        public ExternalIds externalIds;
        public List<Season> seasons;
        public VideosResponse.Video video;

        //        FOLLOWING
        public boolean fav = false;
        public Date addedDate;
        public Date finishDate;

        public Serie() {

        }


        public BasicResponse.SerieBasic toBasic() {
            return new BasicResponse.SerieBasic(this.id, this.name, this.posterPath, this.voteAverage);
        }

        public void checkFav(List<Serie> seriesFavs) {
            for (Serie s : seriesFavs) {
                if (this.id == s.id) {
                    this.fav = true;
                    checkSeason(s);
                    break;
                }
            }
        }

        private void checkSeason(Serie s) {
            for (int i = 0; i < s.seasons.size(); i++) {
                this.seasons.get(i).visto = s.seasons.get(i).visto;
                for (int j = 0; j < s.seasons.get(i).episodes.size(); j++) {
                    this.seasons.get(i).episodes.get(j).visto = s.seasons.get(i).episodes.get(j).visto;
                }
            }
        }

        public int getPosition(List<Serie> favs) {
            for (int i = 0; i < favs.size(); i++) {
                if (favs.get(i).id == this.id) {
                    return i;
                }
            }
            return -1;
        }

        public static class Genre implements Serializable {
            @SerializedName("name")
            @Expose
            public String name;

            public Genre() {

            }
        }

        public static class Network implements Serializable {
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("logo_path")
            @Expose
            public String logoPath;

            public Network() {

            }
        }

        public static class ExternalIds implements Serializable {

            @SerializedName("imdb_id")
            @Expose
            public String imdbId;
            @SerializedName("instagram_id")
            @Expose
            public String instagramId;
            @SerializedName("twitter_id")
            @Expose
            public String twitterId;

            public ExternalIds() {
            }
        }
    }
}
