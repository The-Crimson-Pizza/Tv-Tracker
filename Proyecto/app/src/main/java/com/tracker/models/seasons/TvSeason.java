
package com.tracker.models.seasons;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvSeason implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episodes")
    @Expose
    public List<Episode> episodes = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("season_number")
    @Expose
    public int seasonNumber;
    public final static Creator<TvSeason> CREATOR = new Creator<TvSeason>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TvSeason createFromParcel(Parcel in) {
            return new TvSeason(in);
        }

        public TvSeason[] newArray(int size) {
            return (new TvSeason[size]);
        }

    }
    ;

    protected TvSeason(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.episodes, (com.tracker.models.seasons.Episode.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
    }

    public TvSeason() {
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
