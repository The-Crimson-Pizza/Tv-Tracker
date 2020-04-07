package com.tracker.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.tracker.R;
import com.tracker.models.series.Serie;
import com.tracker.util.Util;

import static com.tracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_NETWORK;
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
        if (mSerie != null) {
            fillText();
            fillImages();
        }
    }

    public void fillSerieSinopsis() {
        if (mSerie != null) {
            fillGeneral();
            fillGenres();
            fillNetworks();
            fillTrailer();
        }
    }

    public void fillTrailer(){
        YouTubePlayerView youTubePlayerView = mVista.findViewById(R.id.youtube_player_view);
        if (mSerie.video != null) {
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                youTubePlayer.cueVideo(mSerie.video.key, 0);
                youTubePlayerView.setVisibility(View.VISIBLE);
            });
        } else {
            youTubePlayerView.setVisibility(View.GONE);
            mVista.findViewById(R.id.trailer_title).setVisibility(View.GONE);
        }
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
    }

    private void fillNetworks() {
        Util util = new Util();
        int networks = mSerie.networks.size();
        if (networks <= 3) {
            for (int i = 1; i < networks + 1; i++) {
                String name = "network" + i;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                ImageView imageView = mVista.findViewById(id);
                util.getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(i - 1).logoPath, imageView, mContext);
                imageView.setVisibility(View.VISIBLE);
            }
        } else {
            for (int j = 1; j <= 3; j++) {
                String name = "network" + j;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                ImageView imageView = mVista.findViewById(id);
                util.getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(j - 1).logoPath, imageView, mContext);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillGenres() {
        int genres = mSerie.genres.size();
        if (genres <= 3) {
            for (int i = 1; i < genres + 1; i++) {
                String name = "genre" + i;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                TextView textView = mVista.findViewById(id);
                textView.setText(mSerie.genres.get(i - 1).name);
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            for (int j = 1; j <= 3; j++) {
                String name = "genre" + j;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                TextView textView = mVista.findViewById(id);
                textView.setText(mSerie.genres.get(j - 1).name);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillImages() {
        ImageView poster = mVista.findViewById(R.id.posterImage);
        ImageView background = mVista.findViewById(R.id.imagen_background);

        new Util().getImage(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster, mContext);
        new Util().getImageNoPlaceholder(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background, mContext);
    }
}
