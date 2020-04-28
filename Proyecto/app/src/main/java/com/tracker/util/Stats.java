package com.tracker.util;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.tracker.R;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stats {

    private Context mContext;
    private static Stats mStats;


    public static Stats getInstance(Context context) {
        if (mStats == null) {
            mStats = new Stats(context);
        }
        return mStats;
    }

    private Stats(Context context) {
        this.mContext = context;
    }

    public String countSeries(List<SerieResponse.Serie> mFavs) {
        return mContext.getString(R.string.number_shows, mFavs.size());
    }

    public String countNumberEpisodesWatched(List<SerieResponse.Serie> mFavs) {
        int contEpisodes = 0;
        for (SerieResponse.Serie serie : mFavs) {
            for (Season season : serie.seasons) {
                for (Episode episode : season.episodes) {
                    if (episode.visto) contEpisodes++;
                }
            }
        }
        return mContext.getString(R.string.episodes_watched_total, contEpisodes);
    }

    public String countTimeEpisodesWatched(List<SerieResponse.Serie> mFavs) {
        int contTime = 0;
        for (SerieResponse.Serie serie : mFavs) {
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

    public String mostWatchedSerie(List<SerieResponse.Serie> mFavs) {
        HashMap<String, Integer> seriesMap = new HashMap<>();
        for (SerieResponse.Serie serie : mFavs) {
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

    private Map<String, Integer> getTopTenGenres(List<SerieResponse.Serie> mFavs) {
        HashMap<String, Integer> countGenres = new HashMap<>();
        for (SerieResponse.Serie serie : mFavs) {
            for (SerieResponse.Serie.Genre genre : serie.genres) {
                countGenres.merge(genre.name, 1, Integer::sum);
            }
        }
        return countGenres.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public List<PieEntry> getPieGenres(List<SerieResponse.Serie> mFavs) {
        List<PieEntry> genresData = new ArrayList<>();
        Map<String, Integer> countGenres = getTopTenGenres(mFavs);
        for (Map.Entry<String, Integer> entry : countGenres.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            genresData.add(new PieEntry((float) value, key));
        }
        return genresData;
    }

    private String toDaysHoursMinutes(int time) {
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

// TODO: 27/04/20 controlar intenret al añadir favoritos


}
