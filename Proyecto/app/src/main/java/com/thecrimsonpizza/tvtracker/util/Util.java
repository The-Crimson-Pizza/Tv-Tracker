package com.thecrimsonpizza.tvtracker.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thecrimsonpizza.tvtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_DEFAULT;

public class Util {

    private Util() {
//        Empty constructor
    }

    public static void getImage(String url, ImageView image, Context contexto) {
        Glide.with(contexto)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_poster)
                        .error(R.drawable.default_poster)
//                        .centerCrop()
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
            return new SimpleDateFormat(format, Locale.getDefault()).format(Objects.requireNonNull(
                    new SimpleDateFormat(FORMAT_DEFAULT, Locale.getDefault()).parse(oldDate)));
        } catch (ParseException e) {
            return oldDate;
        }
    }

    public static String formatDateToString(Date oldDate, String pattern) {
        if (oldDate != null)
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(oldDate);
        return "No data";
    }


    public static String getLanguage() {
        return Locale.getDefault().toString().replace("_", "-");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = null;
        if (connectivityManager != null)
            capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }
}
