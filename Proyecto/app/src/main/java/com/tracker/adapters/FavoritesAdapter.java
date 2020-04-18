package com.tracker.adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.models.SerieFav;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_SERIE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<SerieFav> mSeriesFavs;
    private static Context mContext;

    public FavoritesAdapter(Context mContext, List<SerieFav> serie) {
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
        holder.bindTo(mSeriesFavs.get(position));
    }

    @Override
    public int getItemCount() {
        if (mSeriesFavs != null) {
            return mSeriesFavs.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView favPoster;
        TextView favName;
        TextView favStatus;
        TextView favVistos;
        TextView next;
        ProgressBar favProgress;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            favPoster = itemView.findViewById(R.id.posterBasic);
            favName = itemView.findViewById(R.id.serie_name);
            favStatus = itemView.findViewById(R.id.episode_fecha);
            favProgress = itemView.findViewById(R.id.progreso);
            favVistos = itemView.findViewById(R.id.vistos);
            next = itemView.findViewById(R.id.next_episode);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SERIE, id);
                Navigation.findNavController(v).navigate(R.id.action_navigation_fav_to_navigation_series, bundle);
            });
        }

        static FavoritesAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_series_following, parent, false);
            return new FavoritesAdapter.ViewHolder(view);
        }

        void bindTo(SerieFav favSerie) {
            if (favSerie != null) {
                sortSeason(favSerie.seasons);
                id = favSerie.id;
                favName.setText(favSerie.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + favSerie.posterPath, favPoster, mContext);
                favStatus.setText(favSerie.status);
                int totalEpisodes = favSerie.numberOfEpisodes;
                int vistos = countEpisodes(favSerie);
                int progress = 0;
                if (vistos > 0) {
                    progress = (vistos * 100) / totalEpisodes;
                }
                favVistos.setText(mContext.getString(R.string.num_vistos, vistos, totalEpisodes));
                favProgress.setProgress(progress);
                next.setText(getLastEpisode(favSerie));

            }
        }

        String getLastEpisode(SerieFav serieFav) {
            for (Season s : serieFav.seasons) {
                for (Episode e : s.episodes) {
                    if (!e.visto) {
                        return String.format(Locale.getDefault(), "%02dx%02d - %s", e.seasonNumber, e.episodeNumber, e.name);
                    }
                }
            }
            return mContext.getString(R.string.just_watch);
        }

        int countEpisodes(SerieFav serieFav) {
            int cont = 0;
            for (Season s : serieFav.seasons) {
                for (Episode e : s.episodes) {
                    if (e.visto) {
                        cont++;
                    }
                }
            }
            return cont;

        }

        private void sortSeason(List<Season> seasons) {
            Collections.sort(seasons, (season1, season2) -> {
                String numSeason1 = String.valueOf(season1.seasonNumber);
                String numSeason2 = String.valueOf(season2.seasonNumber);
                if (numSeason1 != null && numSeason2 != null) {
                    return numSeason1.compareTo(numSeason2);
                } else {
                    String fecha1 = season1.airDate;
                    String fecha2 = season2.airDate;
                    return fecha1.compareTo(fecha2);
                }
            });
        }
    }
}
