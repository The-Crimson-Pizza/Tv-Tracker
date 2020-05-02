
package com.thecrimsonpizza.tvtracker.models.seasons;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Episode implements Serializable {

    @SerializedName("air_date")
    public String airDate;
    @SerializedName("episode_number")
    public int episodeNumber;
    public int id;
    public String name;
    public String overview;
    @SerializedName("season_number")
    public int seasonNumber;
    @SerializedName("show_id")
    public int showId;
    @SerializedName("still_path")
    public String stillPath;
    @SerializedName("vote_average")
    public float voteAverage;

    public boolean visto = false;
    public Date watchedDate;


    public Episode() {
    }


    public Date getWatchedDate() {
        return watchedDate;
    }

}
