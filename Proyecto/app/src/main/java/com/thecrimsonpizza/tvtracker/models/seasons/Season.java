
package com.thecrimsonpizza.tvtracker.models.seasons;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Season implements Serializable {

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

    public boolean visto = false;
    public Date watchedDate;


    public Season() {
    }

    public static void sortSeason(List<Season> seasons) {
        Collections.sort(seasons, (season1, season2) -> {
            String numSeason1 = String.valueOf(season1.seasonNumber);
            String numSeason2 = String.valueOf(season2.seasonNumber);
            return numSeason1.compareTo(numSeason2);
        });
    }

}
