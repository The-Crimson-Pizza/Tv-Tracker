package com.tracker.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.tracker.R;
import com.tracker.models.series.SerieResponse;
import com.tracker.util.Util;

import static com.tracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_NETWORK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

public class FillSerie {

    private View mView;
    private SerieResponse.Serie mSerie;
    private Context mContext;

    public FillSerie(View view, SerieResponse.Serie serie, Context context) {
        this.mView = view;
        this.mSerie = serie;
        this.mContext = context;
    }

    public void fillCollapseBar() {
        if (mSerie != null) {
            fillBasics();
            fillImages();
        }
    }

    public void fillOverview() {
        if (mSerie != null) {
            ReadMoreTextView overview = mView.findViewById(R.id.sinopsis_text);
            overview.setText(mSerie.overview);

            fillGenres();
            fillNetworks();
            fillTrailer();
        }
    }

    private void fillTrailer() {
        YouTubePlayerView youTubePlayerView = mView.findViewById(R.id.youtube_player_view);
        if (mSerie.video != null) {
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                youTubePlayer.cueVideo(mSerie.video.key, 0);
                youTubePlayerView.setVisibility(View.VISIBLE);
            });
        } else {
            youTubePlayerView.setVisibility(View.GONE);
            mView.findViewById(R.id.trailer_title).setVisibility(View.GONE);
        }
    }

    private void fillBasics() {
        CollapsingToolbarLayout collapseToolbar = mView.findViewById(R.id.toolbar_layout);
        TextView airDate = mView.findViewById(R.id.fechaSerie);
        TextView country = mView.findViewById(R.id.paisSerie);
        TextView status = mView.findViewById(R.id.emisionSerie);
        airDate.setText(mSerie.firstAirDate);


        if (!mSerie.originCountry.isEmpty()) {
            country.setText(new Util().checkExist(mSerie.originCountry.get(0), mContext));
        } else {
            country.setText(mContext.getString(R.string.no_data));
        }

        status.setText(mSerie.status);
        collapseToolbar.setTitle(mSerie.name);
    }

    private void fillNetworks() {
        int networks = mSerie.networks.size();
        if (networks <= 3) {
            for (int i = 1; i <= networks; i++) {
                String name = "network" + i;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                ImageView imageView = mView.findViewById(id);
                new Util().getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(i - 1).logoPath, imageView, mContext);
                imageView.setVisibility(View.VISIBLE);
            }
        } else {
            for (int j = 1; j <= 3; j++) {
                String name = "network" + j;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                ImageView imageView = mView.findViewById(id);
                new Util().getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(j - 1).logoPath, imageView, mContext);
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
                TextView textView = mView.findViewById(id);
                textView.setText(mSerie.genres.get(i - 1).name);
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            for (int j = 1; j <= 3; j++) {
                String name = "genre" + j;
                int id = mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
                TextView textView = mView.findViewById(id);
                textView.setText(mSerie.genres.get(j - 1).name);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillImages() {
        ImageView poster = mView.findViewById(R.id.posterImage);
        ImageView background = mView.findViewById(R.id.imagen_background);

        new Util().getImage(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster, mContext);
        new Util().getImageNoPlaceholder(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background, mContext);
    }
}
