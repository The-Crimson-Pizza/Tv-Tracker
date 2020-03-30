
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextEpisodeToAir implements Parcelable
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
    public Object stillPath;
    @SerializedName("vote_average")
    @Expose
    public int voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    public final static Creator<NextEpisodeToAir> CREATOR = new Creator<NextEpisodeToAir>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NextEpisodeToAir createFromParcel(Parcel in) {
            return new NextEpisodeToAir(in);
        }

        public NextEpisodeToAir[] newArray(int size) {
            return (new NextEpisodeToAir[size]);
        }

    }
    ;

    protected NextEpisodeToAir(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.productionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.showId = ((int) in.readValue((int.class.getClassLoader())));
        this.stillPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.voteAverage = ((int) in.readValue((int.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
    }

    public NextEpisodeToAir() {
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
