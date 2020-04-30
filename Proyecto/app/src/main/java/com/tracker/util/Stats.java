package com.tracker.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
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

    private final Context mContext;
    @SuppressLint("StaticFieldLeak")
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
        return String.valueOf(mFavs.size());
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
        return String.valueOf(contEpisodes);
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
        if (seriesMap.isEmpty()) {
            return "";
        }
        return Collections.max(seriesMap.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
    }

    private Map<String, Integer> getTopTenGenres(List<SerieResponse.Serie> mFavs) {
        HashMap<String, Integer> countGenres = new HashMap<>();
        for (SerieResponse.Serie serie : mFavs) {
            if (serie.genres != null) {
                for (SerieResponse.Serie.Genre genre : serie.genres) {
                    countGenres.merge(genre.name, 1, Integer::sum);
                }
            }
        }
        return countGenres.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<String, Integer> getTopTenNetworks(List<SerieResponse.Serie> mFavs) {
        HashMap<String, Integer> countGenres = new HashMap<>();
        for (SerieResponse.Serie serie : mFavs) {
            if (serie.networks != null) {
                for (SerieResponse.Serie.Network network : serie.networks) {
                    countGenres.merge(network.name, 1, Integer::sum);
                }
            }
        }
        return countGenres.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public List<DataEntry> geNetworks(List<SerieResponse.Serie> mFavs) {
        List<DataEntry> networkData = new ArrayList<>();
        Map<String, Integer> countNetworks = getTopTenNetworks(mFavs);
        for (Map.Entry<String, Integer> entry : countNetworks.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            networkData.add(new ValueDataEntry(key, value));
        }
        return networkData;
    }

    public List<DataEntry> getPieGenres(List<SerieResponse.Serie> mFavs) {
        List<DataEntry> genresData = new ArrayList<>();
        Map<String, Integer> countGenres = getTopTenGenres(mFavs);
        for (Map.Entry<String, Integer> entry : countGenres.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            genresData.add(new ValueDataEntry(key, value));
        }
        return genresData;
    }

    private String toDaysHoursMinutes(int time) {
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
}
