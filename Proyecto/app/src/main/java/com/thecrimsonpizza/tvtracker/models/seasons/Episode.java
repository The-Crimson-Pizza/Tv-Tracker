
package com.thecrimsonpizza.tvtracker.models.seasons;

import android.view.View;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;
import com.thecrimsonpizza.tvtracker.data.FirebaseDb;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * Checks if the episode is watched and sets it true or false the CheckBox
     *
     * @param episode   episode of the season
     * @param serie     serie loaded in the SerieFragment
     * @param favs      list of series in the following list
     * @param seasonPos position of the season in the season list
     */
    public void setWatchedCheck(Episode episode, SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int seasonPos, int episodePos, MaterialCheckBox watchedCheck) {
        if (serie.added) {
            watchedCheck.setVisibility(View.VISIBLE);
            watchedCheck.setChecked(episode.visto);
            watchedCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!episode.visto) {
                        watchEpisode(serie, favs, episodePos, seasonPos);
                    }
                } else {
                    if (episode.visto) {
                        unwatchEpisode(serie, favs, episodePos, seasonPos);
                    }
                }
            });
        }
    }

    /**
     * Set as watched the episode in the RecyclerView and then in the Database
     *
     * @param serie      serie loaded in the SerieFragment
     * @param favs       list of series in the following list
     * @param episodePos episode position in the RecyclerView
     * @param seasonPos  position of the season in the season list
     */
    public void watchEpisode(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int episodePos, int seasonPos) {
        int favPosition = serie.getPosition(favs);
        if (favPosition != -1) {
            favs.get(favPosition)
                    .seasons.get(seasonPos)
                    .episodes.get(episodePos)
                    .visto = true;
            favs.get(favPosition)
                    .seasons.get(seasonPos)
                    .episodes.get(episodePos)
                    .watchedDate = new Date();

            if (checkEpisodesFinished(favs.get(favPosition))) {
                favs.get(favPosition)
                        .seasons.get(seasonPos).visto = true;
                favs.get(favPosition)
                        .seasons.get(seasonPos).watchedDate = new Date();
            } else {
                favs.get(favPosition)
                        .seasons.get(seasonPos).visto = false;
                favs.get(favPosition)
                        .seasons.get(seasonPos).watchedDate = null;
            }

            if (checkSeasonFinished(favs.get(favPosition))) {
                favs.get(favPosition).finished = true;
                favs.get(favPosition).finishDate = new Date();
            } else {
                favs.get(favPosition).finished = false;
                favs.get(favPosition).finishDate = null;
            }

            FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(favs);
        }
    }

    /**
     * Set as unwatched the episode in the RecyclerView and the in the Database
     *
     * @param serie      serie loaded in the SerieFragment
     * @param favs       list of series in the following list
     * @param episodePos episode position in the RecyclerView
     * @param seasonPos  position of the season in the season list
     */
    private void unwatchEpisode(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int episodePos, int seasonPos) {
        int favPosition = serie.getPosition(favs);
        if (favPosition != -1) {
            favs.get(favPosition)
                    .seasons.get(seasonPos)
                    .episodes.get(episodePos)
                    .visto = false;
            favs.get(favPosition)
                    .seasons.get(seasonPos)
                    .episodes.get(episodePos)
                    .watchedDate = null;

            favs.get(favPosition)
                    .seasons.get(seasonPos).visto = false;
            favs.get(favPosition)
                    .seasons.get(seasonPos).watchedDate = null;

            favs.get(favPosition).finished = false;
            favs.get(favPosition).finishDate = null;

            FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(favs);
        }
    }

    /**
     * Checks if the season is finished or not
     *
     * @param serie we need its seasons
     * @return true or false
     */
    private boolean checkSeasonFinished(SerieResponse.Serie serie) {
        for (Season s : serie.seasons) {
            if (!s.visto) return false;
        }
        return true;
    }

    /**
     * Checks if the episodes are watched so the season is finished or not
     *
     * @param serie we need its seasons and episodes
     * @return true or false
     */
    private boolean checkEpisodesFinished(SerieResponse.Serie serie) {
        for (Season s : serie.seasons) {
            for (Episode e : s.episodes) {
                if (!e.visto) return false;
            }
        }
        return true;
    }

}
