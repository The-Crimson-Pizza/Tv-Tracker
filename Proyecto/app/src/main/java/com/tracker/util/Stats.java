package com.tracker.util;

import android.content.Context;

import com.tracker.R;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Stats {

    private List<SerieResponse.Serie> mFavs;
    private Context mContext;
    private static Stats mStats;

    public static Stats getInstance(List<SerieResponse.Serie> favs, Context context) {
        if (mStats == null) {
            mStats = new Stats(favs, context);
        }
        return mStats;
    }

    private Stats(List<SerieResponse.Serie> favs, Context context) {
        this.mFavs = favs;
        this.mContext = context;
    }

    public String countSeries() {
        return mContext.getString(R.string.number_shows, mFavs.size());
    }

    public String countNumberEpisodesWatched() {
        int contEpisodes = 0;
        for (SerieResponse.Serie serie : this.mFavs) {
            for (Season season : serie.seasons) {
                for (Episode episode : season.episodes) {
                    if (episode.visto) contEpisodes++;
                }
            }
        }
        return mContext.getString(R.string.episodes_watched_total, contEpisodes);
    }

    public String countTimeEpisodesWatched() {
        int contTime = 0;
        for (SerieResponse.Serie serie : this.mFavs) {
            for (Season season : serie.seasons) {
                for (Episode episode : season.episodes) {
                    if (episode.visto && !serie.episodeRunTime.isEmpty()) {
                        contTime += serie.episodeRunTime.get(0);
                    }
                }
            }
        }
        return toDaysHoursMinutes(contTime);
    }

    public String mostWatchedSerie() {
        HashMap<String, Integer> seriesMap = new HashMap<>();
        for (SerieResponse.Serie serie : this.mFavs) {
            int contEpisodes = 0;
            for (Season season : serie.seasons) {
                for (Episode episode : season.episodes) {
                    if (episode.visto) {
                        seriesMap.put(serie.name, ++contEpisodes);
                    }
                }
            }
        }
        return Collections.max(seriesMap.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
    }

    void getTopFiveGenres() {
        HashMap<String, Integer> countGenres = new HashMap<>();
        for (SerieResponse.Serie serie : mFavs) {
            for (SerieResponse.Serie.Genre genre : serie.genres) {
                countGenres.merge(genre.name, 1, Integer::sum);
            }
        }

    }

    private String toDaysHoursMinutes(int time) {
        int totalDays = 364;

//        int hola = (int) TimeUnit.MINUTES.toDays(time);
        int minutesInYear = 60 * 24 * 365;
        int year = time / minutesInYear;
        int days = (time / 24 / 60) % 365;
        int hours = time / 60 % 24;
        int minutes = time % 60;
        if (days == 0) {
            return mContext.getString(R.string.hours_watched_total, hours, minutes);
        } else if (year == 0) {
            return mContext.getString(R.string.days_watched_total, days, hours, minutes);
        } else {
            return mContext.getString(R.string.year_watched_total, year, days, hours, minutes);
        }
    }


/*    todo - 
                tiempo total viendo cosas -------> LAST WEEK/ALL TIME - 84 days, 11 hours, 37 minutos watching, 2,473 episodes (2,523 plays of 194 shows)
                serie con mas minutos vista,
                dias de la semana que se ven mas series
                géneros mas vistos
                last watched

  */


}
