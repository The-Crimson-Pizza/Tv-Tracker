package com.tracker.models.actor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCredits implements Parcelable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;

    public final static Creator<MovieCredits> CREATOR = new Creator<MovieCredits>() {

        @SuppressWarnings({
                "unchecked"
        })
        public MovieCredits createFromParcel(Parcel in) {
            return new MovieCredits(in);
        }

        public MovieCredits[] newArray(int size) {
            return (new MovieCredits[size]);
        }

    };

    protected MovieCredits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
    }

    public MovieCredits() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
    }

    public int describeContents() {
        return 0;
    }


    public class Cast implements Parcelable {

        public int id;
        public String title;
        public String character;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("original_title")
        @Expose
        public String originalTitle;
        @SerializedName("release_date")
        @Expose
        public String releaseDate;


        public final Parcelable.Creator<Cast> CREATOR = new Creator<Cast>() {


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
            this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((int) in.readValue((int.class.getClassLoader())));
            this.title = ((String) in.readValue((String.class.getClassLoader())));
            this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
            this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
            this.character = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Cast() {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(posterPath);
            dest.writeValue(id);
            dest.writeValue(title);
            dest.writeValue(originalTitle);
            dest.writeValue(releaseDate);
            dest.writeValue(character);
        }

        public int describeContents() {
            return 0;
        }
    }
}