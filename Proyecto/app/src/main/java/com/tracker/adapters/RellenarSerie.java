package com.tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.series.Serie;
import com.tracker.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.TAG;

public class RellenarSerie {

    private View mVista;
    private Serie mSerie;
    private Context mContext;

    public RellenarSerie(View vista, Serie serie, Context context) {
        this.mVista = vista;
        this.mSerie = serie;
        this.mContext = context;
    }

    public void fillSerie() {
        fillImages();
    }

    void fillImages() {

        CollapsingToolbarLayout uno = mVista.findViewById(R.id.toolbar_layout);
        ImageView poster = mVista.findViewById(R.id.posterImage);
        TextView fecha = mVista.findViewById(R.id.fechaSerie);

//        new Util().usePicasso(BASE_URL_IMAGES_BACK + mSerie.backdropPath, uno);

        new Util().usePicasso(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster);
        uno.setTitle(mSerie.name);
        fecha.setText(mSerie.firstAirDate);


    }


}
