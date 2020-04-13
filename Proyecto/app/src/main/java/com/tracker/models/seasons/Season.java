
package com.tracker.models.seasons;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season implements Parcelable
{

    public int id;
    @SerializedName("air_date")
    public String airDate;
    public List<Episode> episodes = null;
    public String name;
    public String overview;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("season_number")
    public int seasonNumber;
    @SerializedName("episode_count")
    public int episodeCount;

    public final static Creator<Season> CREATOR = new Creator<Season>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        public Season[] newArray(int size) {
            return (new Season[size]);
        }

    }
    ;

    protected Season(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.episodes, (com.tracker.models.seasons.Episode.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Season() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeList(episodes);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(id);
        dest.writeValue(posterPath);
        dest.writeValue(seasonNumber);
    }

    public int describeContents() {
        return  0;
    }

}
