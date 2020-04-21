package com.tracker.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.tracker.util.Constants.FORMAT_DEFAULT;

public class Util {

    private Util() {

    }

    public static void getImage(String url, ImageView image, Context contexto) {
        Glide.with(contexto)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_poster)
                        .error(R.drawable.default_poster)
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);
    }

    public static void getImageNoPlaceholder(String url, ImageView image, Context contexto) {
        Glide.with(contexto)
                .load(url)
                .into(image);
    }

    public static void getImagePortrait(String url, ImageView image, Context contexto) {

        Glide.with(contexto)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_poster)
                        .error(R.drawable.default_portrait_big)
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);
    }

    public static String checkNull(String data, Context context) {
        return data != null && data.length() > 0 ? data : context.getString(R.string.no_data);
    }

    public static String convertStringDateFormat(String oldDate, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(new SimpleDateFormat(FORMAT_DEFAULT, Locale.getDefault()).parse(oldDate));
        } catch (ParseException e) {
            return oldDate;
        }
    }

    public static String formatDateToString(Date oldDate, String pattern) {
        if (oldDate != null) {
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(oldDate);
        }
        return "No data";
    }


    public static String getLanguage() {
        return Locale.getDefault().toString().replace("_", "-");
    }

}
