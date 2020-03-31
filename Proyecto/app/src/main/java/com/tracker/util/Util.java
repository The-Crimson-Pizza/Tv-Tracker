package com.tracker.util;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tracker.R;

public class Util {

    public void usePicasso(String url, ImageView image) {
        Picasso.get()
                .load(url)
                .noFade()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.default_poster)
                .into(image, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Picasso.get()
                        .load(url)
                        .noFade()
                        .placeholder(R.drawable.default_poster)
                        .into(image);
            }
        });
    }
}
