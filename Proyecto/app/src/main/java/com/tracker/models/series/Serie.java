
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Serie implements Parcelable
{

    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("created_by")
    @Expose
    public List<CreatedBy> createdBy = null;
    @SerializedName("episode_run_time")
    @Expose
    public List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("genres")
    @Expose
    public List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("in_production")
    @Expose
    public boolean inProduction;
    @SerializedName("languages")
    @Expose
    public List<String> languages = null;
    @SerializedName("last_air_date")
    @Expose
    public String lastAirDate;
    @SerializedName("last_episode_to_air")
    @Expose
    public LastEpisodeToAir lastEpisodeToAir;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("next_episode_to_air")
    @Expose
    public NextEpisodeToAir nextEpisodeToAir;
    @SerializedName("networks")
    @Expose
    public List<Network> networks = null;
    @SerializedName("number_of_episodes")
    @Expose
    public int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    public int numberOfSeasons;
    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = null;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("production_companies")
    @Expose
    public List<ProductionCompany> productionCompanies = null;
    @SerializedName("seasons")
    @Expose
    public List<Season> seasons = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("credits")
    @Expose
    public Credits credits;
    @SerializedName("similar")
    @Expose
    public Similar similar;
    @SerializedName("external_ids")
    @Expose
    public ExternalIds externalIds;
    @SerializedName("videos")
    @Expose
    public Videos videos;
    @SerializedName("images")
    @Expose
    public Images images;
    public final static Creator<Serie> CREATOR = new Creator<Serie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        public Serie[] newArray(int size) {
            return (new Serie[size]);
        }

    }
    ;

    protected Serie(Parcel in) {
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.createdBy, (CreatedBy.class.getClassLoader()));
        in.readList(this.episodeRunTime, (Integer.class.getClassLoader()));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genres, (Genre.class.getClassLoader()));
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.inProduction = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.languages, (String.class.getClassLoader()));
        this.lastAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.lastEpisodeToAir = ((LastEpisodeToAir) in.readValue((LastEpisodeToAir.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.nextEpisodeToAir = ((NextEpisodeToAir) in.readValue((NextEpisodeToAir.class.getClassLoader())));
        in.readList(this.networks, (Network.class.getClassLoader()));
        this.numberOfEpisodes = ((int) in.readValue((int.class.getClassLoader())));
        this.numberOfSeasons = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.originCountry, (String.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((float) in.readValue((float.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.productionCompanies, (ProductionCompany.class.getClassLoader()));
        in.readList(this.seasons, (Season.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
        this.credits = ((Credits) in.readValue((Credits.class.getClassLoader())));
        this.similar = ((Similar) in.readValue((Similar.class.getClassLoader())));
        this.externalIds = ((ExternalIds) in.readValue((ExternalIds.class.getClassLoader())));
        this.videos = ((Videos) in.readValue((Videos.class.getClassLoader())));
        this.images = ((Images) in.readValue((Images.class.getClassLoader())));
    }

    public Serie() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(backdropPath);
        dest.writeList(createdBy);
        dest.writeList(episodeRunTime);
        dest.writeValue(firstAirDate);
        dest.writeList(genres);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(inProduction);
        dest.writeList(languages);
        dest.writeValue(lastAirDate);
        dest.writeValue(lastEpisodeToAir);
        dest.writeValue(name);
        dest.writeValue(nextEpisodeToAir);
        dest.writeList(networks);
        dest.writeValue(numberOfEpisodes);
        dest.writeValue(numberOfSeasons);
        dest.writeList(originCountry);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalName);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        dest.writeList(productionCompanies);
        dest.writeList(seasons);
        dest.writeValue(status);
        dest.writeValue(type);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(credits);
        dest.writeValue(similar);
        dest.writeValue(externalIds);
        dest.writeValue(videos);
        dest.writeValue(images);
    }

    public int describeContents() {
        return  0;
    }

}
