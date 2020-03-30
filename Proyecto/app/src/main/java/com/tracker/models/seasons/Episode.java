
package com.tracker.models.seasons;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable
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
    public float voteAverage;

    public final static Creator<Episode> CREATOR = new Creator<Episode>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        public Episode[] newArray(int size) {
            return (new Episode[size]);
        }

    }
    ;

    protected Episode(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
        this.showId = ((int) in.readValue((int.class.getClassLoader())));
        this.stillPath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
    }

    public Episode() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeValue(episodeNumber);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(seasonNumber);
        dest.writeValue(showId);
        dest.writeValue(stillPath);
        dest.writeValue(voteAverage);
    }

    public int describeContents() {
        return  0;
    }

}
