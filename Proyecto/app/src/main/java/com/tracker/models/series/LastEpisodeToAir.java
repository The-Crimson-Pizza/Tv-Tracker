
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastEpisodeToAir implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episode_number")
    @Expose
    public int episodeNumber;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("production_code")
    @Expose
    public String productionCode;
    @SerializedName("season_number")
    @Expose
    public int seasonNumber;
    @SerializedName("show_id")
    @Expose
    public int showId;
    @SerializedName("still_path")
    @Expose
    public String stillPath;
    @SerializedName("vote_average")
    @Expose
    public int voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    public final static Creator<LastEpisodeToAir> CREATOR = new Creator<LastEpisodeToAir>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LastEpisodeToAir createFromParcel(Parcel in) {
            return new LastEpisodeToAir(in);
        }

        public LastEpisodeToAir[] newArray(int size) {
            return (new LastEpisodeToAir[size]);
        }

    }
    ;

    protected LastEpisodeToAir(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.productionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.showId = ((int) in.readValue((int.class.getClassLoader())));
        this.stillPath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((int) in.readValue((int.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
    }

    public LastEpisodeToAir() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeValue(episodeNumber);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(productionCode);
        dest.writeValue(seasonNumber);
        dest.writeValue(showId);
        dest.writeValue(stillPath);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
    }

    public int describeContents() {
        return  0;
    }

}
