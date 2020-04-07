package com.tracker.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tracker.R;

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

}
