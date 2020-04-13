package com.tracker.models.actor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCredits implements Parcelable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;

    public final static Creator<TvCredits> CREATOR = new Creator<TvCredits>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TvCredits createFromParcel(Parcel in) {
            return new TvCredits(in);
        }

        public TvCredits[] newArray(int size) {
            return (new TvCredits[size]);
        }

    };

    protected TvCredits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
    }

    public TvCredits() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
    }

    public int describeContents() {
        return 0;
    }

    public class Cast implements Parcelable {

        @SerializedName("original_name")
        public String originalName;
        public String name;
        public String character;
        @SerializedName("episode_count")
        public int episodeCount;
        public int id;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("first_air_date")
        public String firstAirDate;

        public final Creator<Cast> CREATOR = new Creator<Cast>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Cast createFromParcel(Parcel in) {
                return new Cast(in);
            }

            public Cast[] newArray(int size) {
                return (new Cast[size]);
            }

        };

        protected Cast(Parcel in) {
            this.originalName = ((String) in.readValue((String.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
            this.character = ((String) in.readValue((String.class.getClassLoader())));
            this.episodeCount = ((int) in.readValue((int.class.getClassLoader())));
            this.id = ((int) in.readValue((int.class.getClassLoader())));
            this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Cast() {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(originalName);
            dest.writeValue(name);
            dest.writeValue(firstAirDate);
            dest.writeValue(character);
            dest.writeValue(episodeCount);
            dest.writeValue(id);
            dest.writeValue(posterPath);
        }

        public int describeContents() {
            return 0;
        }

    }
}
