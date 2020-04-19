package com.tracker.models.actor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieCredits implements Serializable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;


    public MovieCredits() {
    }

    public class Cast implements Serializable {

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
        @SerializedName("vote_average")
        public float voteAverage;


        public Cast() {
        }

    }
}