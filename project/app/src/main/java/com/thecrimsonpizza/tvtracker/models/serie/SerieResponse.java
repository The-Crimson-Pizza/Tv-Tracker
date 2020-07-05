
package com.thecrimsonpizza.tvtracker.models.serie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thecrimsonpizza.tvtracker.models.BasicResponse;
import com.thecrimsonpizza.tvtracker.models.seasons.Episode;
import com.thecrimsonpizza.tvtracker.models.seasons.Season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SerieResponse {

    @SerializedName("results")
    @Expose
    public final List<Serie> results;

    public SerieResponse(List<Serie> results) {
        this.results = results;
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
        @SerializedName("next_episode_to_air")
        public Episode nextEpisodeToAir;
        @SerializedName("last_episode_to_air")
        public Episode lastEpisodeToAir;

        //        FOLLOWING
        public boolean added = false;
        public boolean finished = false;
        public Date addedDate;
        public Date finishDate;
        public Episode lastEpisodeWatched;

        public Serie() {
//            Empty constructor for Firebase serialize
        }

        public Serie withSeasons(List<Season> seasonsList, Serie serie) {
            serie.seasons = seasonsList;
            return serie;
        }


        public BasicResponse.SerieBasic toBasic() {
            return new BasicResponse.SerieBasic(this.id, this.name, this.posterPath, this.voteAverage);
        }

        public void checkFav(List<Serie> seriesFavs) {
            for (Serie s : seriesFavs) {
                if (this.id == s.id) {
                    this.added = true;
                    setSeasonWatched(s);
                    break;
                }
            }
        }

        private void setSeasonWatched(Serie serie) {
            int cont = 0;
            for (int i = 0; i < serie.seasons.size(); i++) {
                this.seasons.get(i).visto = serie.seasons.get(i).visto;
                this.seasons.get(i).watchedDate = serie.seasons.get(i).watchedDate;
                setEpisodeWatched(serie, i);
                if (this.seasons.get(i).visto) cont++;
            }
            isSerieFinished(cont);
        }

        private void setEpisodeWatched(Serie serie, int i) {
            for (int j = 0; j < serie.seasons.get(i).episodes.size(); j++) {
                this.seasons.get(i).episodes.get(j).visto = serie.seasons.get(i).episodes.get(j).visto;
                this.seasons.get(i).episodes.get(j).watchedDate = serie.seasons.get(i).episodes.get(j).watchedDate;
            }
            if (checkAllEpisodes(serie.seasons.get(i).episodes)) {
                this.seasons.get(i).visto = true;
                this.seasons.get(i).watchedDate = getMaxDate(getDatesEpisodes(serie.seasons.get(i).episodes));
            } else {
                this.seasons.get(i).visto = false;
                this.seasons.get(i).watchedDate = null;
            }
        }

        private void isSerieFinished(int cont) {
            if (this.seasons.size() == cont) {
                this.finished = true;
                this.finishDate = getMaxDate(getDatesSeason(this.seasons));
            } else {
                this.finished = false;
                this.finishDate = null;
            }
        }

        private boolean checkAllEpisodes(List<Episode> episodes) {
            int cont = 0;
            for (int i = 0; i < episodes.size(); i++) {
                if (episodes.get(i).visto) {
                    cont++;
                }
            }
            return cont == episodes.size();
        }

        private Date getMaxDate(List<Date> datesList) {
            if (datesList.isEmpty()) {
                return null;
            }
            return Collections.max(datesList);
        }

        List<Date> getDatesEpisodes(List<Episode> episodes) {
            List<Date> dates = new ArrayList<>();
            for (Episode e : episodes) {
                if (e.watchedDate != null) {
                    dates.add(e.watchedDate);
                }
            }
            return dates;
        }

        private List<Date> getDatesSeason(List<Season> seasons) {
            List<Date> dates = new ArrayList<>();
            for (Season s : seasons) {
                if (s.watchedDate != null) {
                    dates.add(s.watchedDate);
                }
            }
            return dates;
        }

        public int getPosition(List<Serie> favs) {
            for (int i = 0; i < favs.size(); i++) {
                if (favs.get(i).id == this.id) {
                    return i;
                }
            }
            return -1;
        }

        public static class Genre implements Parcelable {

            public int id;
            public String name;

            public Genre() {
//                 Empty constructor for Firebase serialize
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
            }

            Genre(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
            }

            public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
                @Override
                public Genre createFromParcel(Parcel source) {
                    return new Genre(source);
                }

                @Override
                public Genre[] newArray(int size) {
                    return new Genre[size];
                }
            };
        }

        public static class Network implements Parcelable {

            public int id;
            public String name;
            @SerializedName("logo_path")
            public String logoPath;

            public Network() {
//                Empty constructor for Firebase serialize
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.logoPath);
            }

            Network(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.logoPath = in.readString();
            }

            public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
                @Override
                public Network createFromParcel(Parcel source) {
                    return new Network(source);
                }

                @Override
                public Network[] newArray(int size) {
                    return new Network[size];
                }
            };
        }

        public static class ExternalIds implements Serializable {

            @SerializedName("imdb_id")
            public String imdbId;
            @SerializedName("instagram_id")
            public String instagramId;

            public ExternalIds() {
//                Empty constructor for Firebase serialize
            }
        }
    }
}
