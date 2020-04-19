
package com.tracker.models.serie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracker.models.SerieFav;
import com.tracker.models.VideosResponse;
import com.tracker.models.seasons.Season;

import java.util.List;

public class SerieResponse {

    @SerializedName("results")
    @Expose
    public final List<Serie> results = null;

    public class Serie implements Parcelable {

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

        public boolean isFav = false;

        public VideosResponse.Video video;
        public List<Season> seasons;


        public SerieFav convertSerieToFav() {
            return new SerieFav(this.id, this.name, this.status,
                    this.posterPath, this.episodeRunTime,
                    this.numberOfEpisodes, this.numberOfSeasons, this.seasons, this.voteAverage);
        }

        public void setSeasons(List<Season> seasons) {
            this.seasons = seasons;
        }

        public void setVideos(VideosResponse.Video video) {
            this.video = video;
        }

        public final Creator<Serie> CREATOR = new Creator<Serie>() {

            public Serie createFromParcel(Parcel in) {
                return new Serie(in);
            }

            public Serie[] newArray(int size) {
                return (new Serie[size]);
            }

        };

        protected Serie(Parcel in) {
            this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(this.episodeRunTime, (Integer.class.getClassLoader()));
            this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(this.genres, (Genre.class.getClassLoader()));
            this.homepage = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((int) in.readValue((int.class.getClassLoader())));
            this.inProduction = ((boolean) in.readValue((boolean.class.getClassLoader())));
            this.lastAirDate = ((String) in.readValue((String.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(this.networks, (Network.class.getClassLoader()));
            this.numberOfEpisodes = ((int) in.readValue((int.class.getClassLoader())));
            this.numberOfSeasons = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(this.originCountry, (String.class.getClassLoader()));
            this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
            this.originalName = ((String) in.readValue((String.class.getClassLoader())));
            this.overview = ((String) in.readValue((String.class.getClassLoader())));
            this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
            this.status = ((String) in.readValue((String.class.getClassLoader())));
            this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
            this.credits = ((Credits) in.readValue((Credits.class.getClassLoader())));
            this.video = ((VideosResponse.Video) in.readValue((VideosResponse.Video.class.getClassLoader())));
            this.similar = ((Similar) in.readValue((Similar.class.getClassLoader())));
            this.externalIds = ((ExternalIds) in.readValue((ExternalIds.class.getClassLoader())));
            this.isFav = ((boolean) in.readValue((boolean.class.getClassLoader())));
        }

        public Serie() {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(backdropPath);
            dest.writeList(episodeRunTime);
            dest.writeValue(firstAirDate);
            dest.writeList(genres);
            dest.writeValue(homepage);
            dest.writeValue(id);
            dest.writeValue(inProduction);
            dest.writeValue(lastAirDate);
            dest.writeValue(name);
            dest.writeList(networks);
            dest.writeValue(numberOfEpisodes);
            dest.writeValue(numberOfSeasons);
            dest.writeList(originCountry);
            dest.writeValue(originalLanguage);
            dest.writeValue(originalName);
            dest.writeValue(overview);
            dest.writeValue(posterPath);
            dest.writeValue(status);
            dest.writeValue(voteAverage);
            dest.writeValue(credits);
            dest.writeValue(similar);
            dest.writeValue(externalIds);
            dest.writeValue(video);
            dest.writeValue(isFav);
        }

        public int describeContents() {
            return 0;
        }

        public class Genre implements Parcelable {
            @SerializedName("name")
            @Expose
            public String name;
            public final Creator<Genre> CREATOR = new Creator<Genre>() {
                @SuppressWarnings({
                        "unchecked"
                })
                public Genre createFromParcel(Parcel in) {
                    return new Genre(in);
                }

                public Genre[] newArray(int size) {
                    return (new Genre[size]);
                }
            };

            protected Genre(Parcel in) {
                this.name = ((String) in.readValue((String.class.getClassLoader())));
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(name);
            }

            public int describeContents() {
                return 0;
            }
        }

        public class Network implements Parcelable {
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("logo_path")
            @Expose
            public String logoPath;
            public final Creator<Network> CREATOR = new Creator<Network>() {
                @SuppressWarnings({
                        "unchecked"
                })
                public Network createFromParcel(Parcel in) {
                    return new Network(in);
                }

                public Network[] newArray(int size) {
                    return (new Network[size]);
                }
            };

            protected Network(Parcel in) {
                this.name = ((String) in.readValue((String.class.getClassLoader())));
                this.logoPath = ((String) in.readValue((String.class.getClassLoader())));
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(name);
                dest.writeValue(logoPath);
            }

            public int describeContents() {
                return 0;
            }
        }

        public class ExternalIds implements Parcelable {

            @SerializedName("imdb_id")
            @Expose
            public String imdbId;
            @SerializedName("instagram_id")
            @Expose
            public String instagramId;
            @SerializedName("twitter_id")
            @Expose
            public String twitterId;
            public final Creator<ExternalIds> CREATOR = new Creator<ExternalIds>() {


                @SuppressWarnings({
                        "unchecked"
                })
                public ExternalIds createFromParcel(Parcel in) {
                    return new ExternalIds(in);
                }

                public ExternalIds[] newArray(int size) {
                    return (new ExternalIds[size]);
                }

            };

            protected ExternalIds(Parcel in) {
                this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
                this.instagramId = ((String) in.readValue((String.class.getClassLoader())));
                this.twitterId = ((String) in.readValue((String.class.getClassLoader())));
            }

            public ExternalIds() {
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(imdbId);
                dest.writeValue(instagramId);
                dest.writeValue(twitterId);
            }

            public int describeContents() {
                return 0;
            }

        }
    }
}
