package com.tracker.models.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TvCredits implements Serializable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;



    public TvCredits() {
    }

    public class Cast implements Serializable {

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
        @SerializedName("vote_average")
        public float voteAverage;


        public Cast() {
        }

    }
}
