package com.tracker.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tracker.R;

public class Util {

    public void getPoster(String url, ImageView image, Context contexto) {

        Glide.with(contexto)
                .load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.default_poster))
                .into(image);
    }

    public void getBackground(String url, ImageView image, Context contexto) {

        Glide.with(contexto)
                .load(url)
                .into(image);
    }

}
