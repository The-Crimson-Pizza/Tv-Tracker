package com.thecrimsonpizza.tvtracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;
import com.thecrimsonpizza.tvtracker.util.Constants;
import com.thecrimsonpizza.tvtracker.util.Util;

import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_BACK;
import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_NETWORK;
import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_YEAR;
import static com.thecrimsonpizza.tvtracker.util.Constants.GENRE;
import static com.thecrimsonpizza.tvtracker.util.Constants.ID;
import static com.thecrimsonpizza.tvtracker.util.Constants.NETWORKS;

/**
 * Fills the data obtained by the api in the SerieFragment
 */
public class FillSerie {

    private final View mView;
    private final SerieResponse.Serie mSerie;
    private final Context mContext;

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

    /**
     * Fills the overview and calls the methods that load genres, networks and trailer
     */
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

    /**
     * Sets the youtube viewer and load the trailer
     */
    private void fillTrailer() {
        YouTubePlayerView youTubePlayerView = mView.findViewById(R.id.youtube_player_view);
        if (mSerie.video != null) {
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                youTubePlayer.cueVideo(mSerie.video.getKey(), 0);
                youTubePlayerView.setVisibility(View.VISIBLE);
            });
        } else {
            youTubePlayerView.setVisibility(View.GONE);
            mView.findViewById(R.id.trailer_title).setVisibility(View.GONE);
        }
    }

    /**
     * Fill the basic serie info
     */
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

    /**
     * Fills the Networks images and sets the click listener
     */
    private void fillNetworks() {
        int cont = 1;
        if (!mSerie.networks.isEmpty()) {
            while (cont <= 3) {
                int pos = cont - 1;
                String name = NETWORKS + cont;
                int id = mContext.getResources().getIdentifier(name, ID, mContext.getPackageName());
                ImageButton imageNetwork = mView.findViewById(id);
                Util.getImageNoPlaceholder(BASE_URL_IMAGES_NETWORK + mSerie.networks.get(cont - 1).logoPath, imageNetwork, mContext);
                imageNetwork.setVisibility(View.VISIBLE);
                imageNetwork.setOnClickListener(v -> goToNetworkFragment(pos, v));
                if (cont == mSerie.networks.size()) break;
                else cont++;
            }
        } else {
            ViewSwitcher switcherNetworks = mView.findViewById(R.id.switcher_networks);
            if (R.id.networks == switcherNetworks.getNextView().getId() || R.id.nodatanetworks == switcherNetworks.getNextView().getId())
                switcherNetworks.showNext();
        }
    }

    /**
     * Sets the data to be sent to NetworkFragment and calls it
     *
     * @param pos position of the list
     * @param v   view of the fragment
     */
    private void goToNetworkFragment(int pos, View v) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ID_NETWORK, mSerie.networks.get(pos));
        if (Util.isNetworkAvailable(mContext)) {
            Navigation.findNavController(v).navigate(R.id.action_navigation_series_to_networkFragment, bundle);
        } else {
            Snackbar.make(v, mContext.getString(R.string.no_conn), BaseTransientBottomBar.LENGTH_LONG)
                    .setAction(R.string.activate_net, v1 -> mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }
    }

    /**
     * Fills the Genre buttons and sets the listener
     */
    private void fillGenres() {
        int cont = 1;
        if (!mSerie.genres.isEmpty()) {
            while (cont <= 3) {
                int pos = cont - 1;
                String name = GENRE + cont;
                int id = mContext.getResources().getIdentifier(name, ID, mContext.getPackageName());
                Button buttonGenre = mView.findViewById(id);
                buttonGenre.setText(mSerie.genres.get(pos).name);
                buttonGenre.setVisibility(View.VISIBLE);
                buttonGenre.setOnClickListener(v -> goToGenreFragment(pos, v));
                if (cont == mSerie.genres.size()) break;
                else cont++;
            }
        } else {
            ViewSwitcher switcherGenres = mView.findViewById(R.id.switcher_genres);
            if (R.id.generos == switcherGenres.getNextView().getId() || R.id.nodatagenres == switcherGenres.getNextView().getId())
                switcherGenres.showNext();
        }
    }

    /**
     * Sets the data to be sent to GenreFragment and calls it
     *
     * @param pos position of the list
     * @param v   view of the fragment
     */
    private void goToGenreFragment(int pos, View v) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ID_GENRE, mSerie.genres.get(pos));
        if (Util.isNetworkAvailable(mContext)) {
            Navigation.findNavController(v).navigate(R.id.action_navigation_series_to_genreFragment, bundle);
        } else {
            Snackbar.make(v, mContext.getString(R.string.no_conn), BaseTransientBottomBar.LENGTH_LONG)
                    .setAction(R.string.activate_net, v1 -> mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }
    }

    /**
     * Fills all the images in the fragment
     */
    private void fillImages() {
        ImageView poster = mView.findViewById(R.id.posterImage);
        ImageView background = mView.findViewById(R.id.imagen_background);
        Util.getImage(BASE_URL_IMAGES_POSTER + mSerie.posterPath, poster, mContext);
        Util.getImageNoPlaceholder(BASE_URL_IMAGES_BACK + mSerie.backdropPath, background, mContext);
    }
}
