package com.tracker.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tracker.R;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.SerieFav;
import com.tracker.models.serie.SerieResponse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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

    public static String getFecha(String oldDate, String format) {
        try {
            return new SimpleDateFormat(format).format(new SimpleDateFormat(FORMAT_DEFAULT).parse(oldDate));
        } catch (ParseException e) {
            return oldDate;
        }
    }


    public static String getLanguage() {
        return Locale.getDefault().toString().replace("_", "-");
    }

}
