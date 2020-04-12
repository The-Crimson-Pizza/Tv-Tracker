package com.tracker.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tracker.R;
import com.tracker.models.people.MovieCredits;
import com.tracker.models.people.TvCredits;
import com.tracker.models.seasons.Season;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Util {

    public void getImage(String url, ImageView image, Context contexto) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.default_poster)
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(contexto)
                .load(url)
                .apply(options)
                .into(image);
    }

    public void getImageNoPlaceholder(String url, ImageView image, Context contexto) {
        Glide.with(contexto)
                .load(url)
                .into(image);
    }

    public String checkExist(String data, Context context) {
        if (data.length() > 0) {
            return data;
        } else {
            return context.getString(R.string.no_data);
        }
    }

    public String getMinutos(int minutes) {
//        int minutes = time / (60 * 1000);
//        int seconds = (minutes / 1000) % 60;
        return String.format(Locale.getDefault(),"%d:%02d", minutes, 0);

    }

    public String getFecha(String oldDate) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE dd, MMMM yyyy");
        try {
            return newFormat.format(Objects.requireNonNull(oldFormat.parse(oldDate)));
        } catch (ParseException e) {
            return oldDate;
        }
    }

    public void sortSeries(List<TvCredits.Cast> series) {
        Collections.sort(series, (serie1, serie2) -> {
            String fecha1 = serie1.firstAirDate;
            String fecha2 = serie2.firstAirDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(serie1.id);
                String id2 = String.valueOf(serie2.id);
                return id2.compareTo(id1);
            }
        });
    }

    public void sortFilms(List<MovieCredits.Cast> films) {
        Collections.sort(films, (film1, film2) -> {
            String fecha1 = film1.releaseDate;
            String fecha2 = film2.releaseDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(film1.id);
                String id2 = String.valueOf(film2.id);
                return id2.compareTo(id1);
            }
        });
    }

    public void sortSeason(List<Season> seasons) {
        Collections.sort(seasons, (season1, season2) -> {
            String fecha1 = String.valueOf(season1.seasonNumber);
            String fecha2 = String.valueOf(season2.seasonNumber);
//            if (fecha1 != null && fecha2 != null) {
                return fecha1.compareTo(fecha2);
//            } else {
//                String name1 = season1.name;
//                String name2 = season2.name;
//                return name1.compareTo(name2);
//            }
        });
    }

    public String getLanguage() {
        return Locale.getDefault().toString();
    }

}
