package com.tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

    public void fillSerieTop() {
        fillText();
        fillImages();
    }

    public void fillSerieSinopsis() {
        fillGeneral();
    }

    private void fillText() {
        CollapsingToolbarLayout collapse = mVista.findViewById(R.id.toolbar_layout);
        TextView fecha = mVista.findViewById(R.id.fechaSerie);
        TextView pais = mVista.findViewById(R.id.paisSerie);
        TextView emision = mVista.findViewById(R.id.emisionSerie);

        fecha.setText(mSerie.firstAirDate);
        if (!mSerie.originCountry.isEmpty()) {
            pais.setText(mSerie.originCountry.get(0));
        } else {
            pais.setText("");
        }
        emision.setText(mSerie.status);
        collapse.setTitle(mSerie.name);
    }

    private void fillGeneral() {
        ReadMoreTextView sinopsis = mVista.findViewById(R.id.sinopsis_text);
        sinopsis.setText(mSerie.overview);

        VideoView trailer = mVista.findViewById(R.id.video_view);

        int genres = mSerie.genres.size();
;        if (genres <= 4) {
            for (int i = 1; i < genres+1; i++) {
                String name = "genre" + i;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                if (id != 0) {
                    TextView textView = mVista.findViewById(id);
                    textView.setText(mSerie.genres.get(i - 1).name);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            for (int j = 1; j <= 4; j++) {
                String name = "genre" + j;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                if (id != 0) {
                    TextView textView = mVista.findViewById(id);
                    textView.setText(mSerie.genres.get(j - 1).name);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void fillImages() {
        ImageView poster = mVista.findViewById(R.id.posterImage);
        ImageView background = mVista.findViewById(R.id.imagen_background);

        new Util().getPoster(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster, mContext);
        new Util().getBackground(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background, mContext);
    }
}
