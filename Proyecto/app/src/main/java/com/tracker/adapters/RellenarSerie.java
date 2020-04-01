package com.tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.models.series.Serie;
import com.tracker.util.Util;

import static com.tracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

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

    private void fillImages() {

        CollapsingToolbarLayout collapse = mVista.findViewById(R.id.toolbar_layout);
        ImageView poster = mVista.findViewById(R.id.posterImage);
        ImageView background = mVista.findViewById(R.id.imagen_background);
        TextView fecha = mVista.findViewById(R.id.fechaSerie);

        Picasso.get()
                .load(BASE_URL_IMAGES_BACK + mSerie.backdropPath)
                .noFade()
                .resize(background.getWidth(), background.getHeight())
                .into(background);
//        new Util().usePicasso(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background);
        Log.d("HOLA",BASE_URL_IMAGES_BACK + mSerie.backdropPath);
        new Util().getPoster(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster);
        collapse.setTitle(mSerie.name);
        fecha.setText(mSerie.firstAirDate);


    }


}
