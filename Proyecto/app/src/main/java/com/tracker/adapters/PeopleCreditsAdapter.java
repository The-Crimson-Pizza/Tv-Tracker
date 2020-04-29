package com.tracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;
import com.tracker.models.actor.MovieCredits;
import com.tracker.models.actor.TvCredits;
import com.tracker.ui.WebViewActivity;
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.Collections;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

/**
 * Adapter for the RecyclerView that hosts the info of an actor's movies and shows
 */
public class PeopleCreditsAdapter extends RecyclerView.Adapter<PeopleCreditsAdapter.ViewHolder> {

    private List<TvCredits.Cast> mSeries;
    private List<MovieCredits.Cast> mMovies;
    private final Context mContext;
    private boolean isMovie = false;

    /**
     * Constructor for the Movie Credits
     *
     * @param context context of the activity
     * @param movies  list of the actor's movies
     * @param movie   check if it is movie or not
     */
    PeopleCreditsAdapter(Context context, List<MovieCredits.Cast> movies, boolean movie) {
        this.mMovies = movies;
        this.mContext = context;
        this.isMovie = movie;
        if (!mMovies.isEmpty()) {
            sortMovies(mMovies);
        }
    }

    /**
     * Constructor for the Tv Credits
     *
     * @param context context of the activity
     * @param tv      list of the actor's shows
     */
    PeopleCreditsAdapter(Context context, List<TvCredits.Cast> tv) {
        this.mSeries = tv;
        this.mContext = context;
        if (!mSeries.isEmpty()) {
            sortShows(mSeries);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (isMovie) {
            holder.bindTo(mMovies.get(position), mContext);
        } else {
            holder.bindTo(mSeries.get(position), mContext);
        }
    }

    @Override
    public int getItemCount() {
        if (isMovie) {
            if (mMovies != null) {
                return mMovies.size();
            }
        } else {
            if (mSeries != null) {
                return mSeries.size();
            }
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView name;
        final TextView rating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            rating = itemView.findViewById(R.id.ratingBasic);
        }

        static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
            return new ViewHolder(view);
        }

        void bindTo(MovieCredits.Cast movie, Context context) {
            name.setText(movie.title);
            Util.getImage(BASE_URL_IMAGES_POSTER + movie.posterPath, image, context);
            rating.setText(String.valueOf(movie.voteAverage));
            itemView.setOnClickListener(v -> goToTmdbMovie(movie, context, v));
        }

        private void goToTmdbMovie(MovieCredits.Cast movie, Context context, View view) {
            Snackbar.make(view, R.string.not_implemented, BaseTransientBottomBar.LENGTH_LONG)
                    .setAction(R.string.open_web, v1 -> context.startActivity(new Intent(context, WebViewActivity.class).putExtra(Constants.URL_WEBVIEW, Constants.BASE_URL_WEB_MOVIE + movie.id))).show();
        }

        void bindTo(TvCredits.Cast serie, Context context) {

            name.setText(serie.name);
            Util.getImage(Constants.BASE_URL_IMAGES_POSTER + serie.posterPath, image, context);
            rating.setText(String.valueOf(serie.voteAverage));
            itemView.setOnClickListener(v -> goToSerie(serie, v));
        }

        private void goToSerie(TvCredits.Cast serie, View view) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ID_SERIE, serie.id);
            if (Util.isNetworkAvailable(itemView.getContext())) {
                Navigation.findNavController(view).navigate(R.id.action_actores_to_series, bundle);
            } else {
                Snackbar.make(view, itemView.getContext().getString(R.string.no_conn), BaseTransientBottomBar.LENGTH_LONG)
                        .setAction(R.string.activate_net, v1 -> itemView.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
            }
        }
    }

    /**
     * Sorts the shows by its released date and if it does not exist, by its id
     *
     * @param series list with cast objects
     */
    private void sortShows(List<TvCredits.Cast> series) {
        Collections.sort(series, (serie1, serie2) -> {
            String fecha1 = serie1.firstAirDate;
            String fecha2 = serie2.firstAirDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(serie1.id);
                String id2 = String.valueOf(serie2.id);
                return id2.compareTo(id1);
            }
        });
    }

    /**
     * Sorts the movies by its released date and if it does not exist, by its id
     *
     * @param movies list with cast objects
     */
    private void sortMovies(List<MovieCredits.Cast> movies) {
        Collections.sort(movies, (movie1, movie2) -> {
            String fecha1 = movie1.releaseDate;
            String fecha2 = movie2.releaseDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(movie1.id);
                String id2 = String.valueOf(movie2.id);
                return id2.compareTo(id1);
            }
        });
    }
}
