package com.tracker.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import static com.tracker.util.Constants.FORMAT_DEFAULT;

public class Util {

    private Util() {

    }

    public static void getImage(String url, ImageView image, Context contexto) {
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

    public static void getImageNoPlaceholder(String url, ImageView image, Context contexto) {
        Glide.with(contexto)
                .load(url)
                .into(image);
    }

    public static void getImagePortrait(String url, ImageView image, Context contexto) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.default_portrait_big)
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(contexto)
                .load(url)
                .apply(options)
                .into(image);
    }

    public static String checkNull(String data, Context context) {
        if (data != null && data.length() > 0) {
            return data;
        }
        return context.getString(R.string.no_data);
    }

    public static String getFecha(String oldDate, String format) {
        SimpleDateFormat oldFormat = new SimpleDateFormat(FORMAT_DEFAULT);
        SimpleDateFormat newFormat = new SimpleDateFormat(format);
        try {
            return newFormat.format(oldFormat.parse(oldDate));
        } catch (ParseException e) {
            return oldDate;
        }
    }


    public static String getLanguage() {
        return Locale.getDefault().toString().replace("_", "-");
    }

}
