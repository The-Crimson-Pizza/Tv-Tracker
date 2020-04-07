package com.tracker.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tracker.R;
import com.tracker.models.people.TvCredits;

import java.util.Comparator;
import java.util.List;

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

    public void ordenarSeries( List<TvCredits.Cast> series){
        Comparator<TvCredits.Cast> compareById = (TvCredits.Cast o1, TvCredits.Cast o2) ->
                o1.firstAirDate.compareTo( o2.firstAirDate);
    }

}
