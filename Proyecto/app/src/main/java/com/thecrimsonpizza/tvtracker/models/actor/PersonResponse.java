
package com.thecrimsonpizza.tvtracker.models.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonResponse {

    @SerializedName("results")
    @Expose
    public final List<Person> results;

    public PersonResponse(List<Person> results) {
        this.results = results;
    }

    public static class Person implements Serializable {

        public int id;
        public String name;
        public String birthday;
        public String deathday;
        public int gender;
        public String biography;
        public String homepage;
        @SerializedName("place_of_birth")
        public String placeOfBirth;
        @SerializedName("profile_path")
        public String profilePath;
        @SerializedName("known_for_department")
        public String known;
        @SerializedName("tv_credits")
        public TvCredits tvCredits;
        @SerializedName("external_ids")
        public ExternalIds externalIds;
        @SerializedName("movie_credits")
        public MovieCredits movieCredits;

        public boolean isDead() {
            return deathday != null;
        }

        public class ExternalIds implements Serializable {

            @SerializedName("instagram_id")
            public String instagramId;
            @SerializedName("twitter_id")
            public String twitterId;
            @SerializedName("imdb_id")
            public String imdbId;

        }
    }
}