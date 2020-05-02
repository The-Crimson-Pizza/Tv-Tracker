package com.thecrimsonpizza.tvtracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.models.seasons.Episode;
import com.thecrimsonpizza.tvtracker.models.seasons.Season;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;
import com.thecrimsonpizza.tvtracker.util.Constants;
import com.thecrimsonpizza.tvtracker.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_HOURS;
import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_LONG;

/**
 * Adapter from the RecyclerView that hosts the Favorites/Following series info
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<SerieResponse.Serie> mSeriesFavs;
    private final Context mContext;

    public FavoritesAdapter(Context mContext, List<SerieResponse.Serie> serie) {
        this.mSeriesFavs = serie;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FavoritesAdapter.ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mContext, mSeriesFavs.get(position));
    }

    @Override
    public int getItemCount() {
        if (mSeriesFavs != null) {
            return mSeriesFavs.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView favPoster;
        final TextView favName;
        final TextView favStatus;
        final TextView favVistos;
        final TextView next;
        final TextView next2;
        final TextView sinopsis;
        final ProgressBar favProgress;
        int id;

        final ConstraintLayout expandableView;
        final Button arrowBtn;
        final LinearLayout cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            favPoster = itemView.findViewById(R.id.posterBasic);
            favName = itemView.findViewById(R.id.serie_name);
            favStatus = itemView.findViewById(R.id.episode_fecha);
            favProgress = itemView.findViewById(R.id.progreso);
            favVistos = itemView.findViewById(R.id.vistos);
            next = itemView.findViewById(R.id.next_episode);
            next2 = itemView.findViewById(R.id.next_episode_2);
            sinopsis = itemView.findViewById(R.id.sinopsis);

            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID_SERIE, id);
                Navigation.findNavController(v).navigate(R.id.action_navigation_fav_to_navigation_series, bundle);
            });

            // CARDVIEW INTERACTION
            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);

            arrowBtn.setOnClickListener(v -> {
                if (expandableView.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.arrow_collapse);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.arrow_expand);
                }
            });
        }

        static FavoritesAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_series_following_card, parent, false);
            return new FavoritesAdapter.ViewHolder(view);
        }

        void bindTo(Context context, SerieResponse.Serie serie) {
            if (serie != null) {
                sortSeason(serie.seasons);
                id = serie.id;
                favName.setText(serie.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serie.posterPath, favPoster, context);
                favStatus.setText(serie.status);
                getEpisodesWatched(context, serie);
                next.setText(getLastEpisode(context, serie));
                next2.setText(getLastEpisode(context, serie));
                sinopsis.setText(getLastEpisodeOverview(context, serie));
            }
        }

        /**
         * Gets the episodes watched and sets progress
         *
         * @param context context of the activity
         * @param serie   favorite serie
         */
        private void getEpisodesWatched(Context context, SerieResponse.Serie serie) {
            int totalEpisodes = serie.numberOfEpisodes;
            int vistos = countEpisodes(serie);
            int progress = 0;
            if (vistos > 0) {
                progress = (vistos * 100) / totalEpisodes;
            }
            favVistos.setText(context.getString(R.string.num_vistos, vistos, totalEpisodes));
            favProgress.setProgress(progress);
        }

        /**
         * Gets the overview of the first unwatched episode
         *
         * @param context context of the activity
         * @param serie   favorite serie
         * @return the overview of the next unwatched episode
         */
        String getLastEpisodeOverview(Context context, SerieResponse.Serie serie) {
            Season.sortSeason(serie.seasons);
            for (Season s : serie.seasons) {
                for (Episode e : s.episodes) {
                    if (!e.visto) {
                        return e.overview.isEmpty() ? context.getString(R.string.no_data) : e.overview;
                    }
                }
            }
            return String.format(context.getString(R.string.finished_date), Util.formatDateToString(serie.finishDate, FORMAT_LONG), Util.formatDateToString(serie.finishDate, FORMAT_HOURS));
        }

        /**
         * Gets the next episode unwatched
         *
         * @param context of the activity to getString
         * @param serie   favorite serie
         * @return number of watched episodes or if we watched every episode
         */
        String getLastEpisode(Context context, SerieResponse.Serie serie) {
            Season.sortSeason(serie.seasons);
            for (Season s : serie.seasons) {
                for (Episode e : s.episodes) {
                    if (!e.visto) {
                        return String.format(Locale.getDefault(), Constants.SEASON_EPISODE_FORMAT, e.seasonNumber, e.episodeNumber, e.name);
                    }
                }
            }
            return context.getString(R.string.just_watch);
        }

        /**
         * Counts the number of watched episodes
         *
         * @param serie favorite serie
         * @return integer with the count of watched episodes
         */
        int countEpisodes(SerieResponse.Serie serie) {
            int cont = 0;
            for (Season s : serie.seasons) {
                for (Episode e : s.episodes) {
                    if (e.visto) {
                        cont++;
                    }
                }
            }
            return cont;
        }

        /**
         * Sorts the seasons by its number
         *
         * @param seasons from the serie we are passing
         */
        private void sortSeason(List<Season> seasons) {
            Collections.sort(seasons, (season1, season2) -> {
                String numSeason1 = String.valueOf(season1.seasonNumber);
                String numSeason2 = String.valueOf(season2.seasonNumber);
                return numSeason1.compareTo(numSeason2);
            });
        }
    }
}
