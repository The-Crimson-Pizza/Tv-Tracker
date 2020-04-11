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

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

    public void ordenarSeries(List<TvCredits.Cast> series) {
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

    public void ordenarPeliculas(List<MovieCredits.Cast> peliculas) {
        Collections.sort(peliculas, (peli1, peli2) -> {
            String fecha1 = peli1.releaseDate;
            String fecha2 = peli2.releaseDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(peli1.id);
                String id2 = String.valueOf(peli2.id);
                return id2.compareTo(id1);
            }
        });
    }
    public void ordenarTemporadas(List<Season> seasons) {
        Collections.sort(seasons, (season1, season2) -> {
            String fecha1 = season1.airDate;
            String fecha2 = season2.airDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha1.compareTo(fecha2);
            } else {
                String name1 = season1.name;
                String name2 = season2.name;
                return name1.compareTo(name2);
            }
        });
    }

    public String getLanguage() {
        return Locale.getDefault().toString();
    }

//    TODO - TRADUCIR STRINGS

}
