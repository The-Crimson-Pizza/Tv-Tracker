package com.tracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.navigation.Navigation;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.tracker.R;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Util;

import static com.tracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_NETWORK;
import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.FORMAT_YEAR;
import static com.tracker.util.Constants.GENRE;
import static com.tracker.util.Constants.ID;
import static com.tracker.util.Constants.ID_GENRE;
import static com.tracker.util.Constants.ID_NETWORK;
import static com.tracker.util.Constants.NETWORKS;

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
            LinearLayout check = mView.findViewById(R.id.seguimiento);
            if (mSerie.added) check.setVisibility(View.VISIBLE);
            else check.setVisibility(View.GONE);

            ReadMoreTextView overview = mView.findViewById(R.id.sinopsis_text);
            overview.setText(Util.checkNull(mSerie.overview, mContext));

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

        airDate.setText(Util.convertStringDateFormat(mSerie.firstAirDate, FORMAT_YEAR));

        if (!mSerie.originCountry.isEmpty()) country.setText(mSerie.originCountry.get(0));
        else country.setText(mContext.getString(R.string.no_data));

        status.setText(mSerie.status);
        collapseToolbar.setTitle(mSerie.name);
    }

    private void fillNetworks() {
        int cont = 1;
        if (!mSerie.networks.isEmpty()) {
            while (cont <= 3) {
                int pos = cont - 1;
                String name = NETWORKS + cont;
                int id = mContext.getResources().getIdentifier(name, ID, mContext.getPackageName());
                ImageButton imageView = mView.findViewById(id);
                Util.getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(cont - 1).logoPath, imageView, mContext);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_NETWORK, mSerie.networks.get(pos).id);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_series_to_networkFragment, bundle);
                });
                if (cont == mSerie.networks.size()) break;
                else cont++;
            }
        } else {
            ViewSwitcher switcherNetworks = mView.findViewById(R.id.switcher_networks);
            if (R.id.networks == switcherNetworks.getNextView().getId() || R.id.nodatanetworks == switcherNetworks.getNextView().getId())
                switcherNetworks.showNext();
        }
    }

    private void fillGenres() {
        int cont = 1;
        if (!mSerie.genres.isEmpty()) {
            while (cont <= 3) {
                int pos = cont - 1;
                String name = GENRE + cont;
                int id = mContext.getResources().getIdentifier(name, ID, mContext.getPackageName());
                Button textView = mView.findViewById(id);
                textView.setText(mSerie.genres.get(pos).name);
                textView.setVisibility(View.VISIBLE);
                textView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_GENRE, mSerie.genres.get(pos).id);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_series_to_genreFragment, bundle);

                });
                if (cont == mSerie.genres.size()) break;
                else cont++;
            }
        } else {
            ViewSwitcher switcherGenres = mView.findViewById(R.id.switcher_genres);
            if (R.id.generos == switcherGenres.getNextView().getId() || R.id.nodatagenres == switcherGenres.getNextView().getId())
                switcherGenres.showNext();
        }
    }

    private void fillImages() {
        ImageView poster = mView.findViewById(R.id.posterImage);
        ImageView background = mView.findViewById(R.id.imagen_background);
        Util.getImage(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster, mContext);
        Util.getImageNoPlaceholder(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background, mContext);
    }
}
