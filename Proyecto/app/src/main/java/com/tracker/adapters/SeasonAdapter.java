package com.tracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.tracker.R;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;
import com.tracker.repositories.FirebaseDb;
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.Date;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Season> mSeasons;
    private final SerieResponse.Serie mSerie;
    private final List<SerieResponse.Serie> mFavs;

    public SeasonAdapter(Context mContext, SerieResponse.Serie serie, List<SerieResponse.Serie> favs) {
        this.mFavs = favs;
        this.mSerie = serie;
        this.mSeasons = serie.seasons;
        this.mContext = mContext;
        if (mSeasons != null) {
            Season.sortSeason(mSeasons);
        }
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SeasonAdapter.ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mSeasons.get(position), mSerie, mFavs, mContext);
    }

    @Override
    public int getItemCount() {
        if (mSeasons != null) {
            return mSeasons.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView seasonPoster;
        final TextView seasonName;
        final TextView numEpisodes;
        final MaterialCheckBox watchedCheck;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonPoster = itemView.findViewById(R.id.image_season);
            seasonName = itemView.findViewById(R.id.season_name);
            numEpisodes = itemView.findViewById(R.id.episode_number);
            watchedCheck = itemView.findViewById(R.id.checkbox_watched);

            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID_SEASON, getAdapterPosition());
                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static SeasonAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_season, parent, false);
            return new SeasonAdapter.ViewHolder(view);
        }

        void bindTo(Season season, SerieResponse.Serie serie, List<SerieResponse.Serie> mFavs, Context context) {
            if (season != null) {
                if (serie.added) {
                    setWatchCheck(season, serie, mFavs);
                }
                seasonName.setText(season.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + season.posterPath, seasonPoster, context);
                if (season.episodes != null) {
                    if (serie.added) {
                        numEpisodes.setText(
                                context.getString(R.string.num_episodes_follow,
                                        countEpisodes(season),
                                        season.episodes.size())
                        );
                    } else {
                        numEpisodes.setText(
                                context.getString(R.string.n_episodes,
                                        season.episodes.size())
                        );
                    }

                } else {
                    numEpisodes.setText(context.getString(R.string.no_data));
                }
            }
        }

        private void setWatchCheck(Season season, SerieResponse.Serie serie, List<SerieResponse.Serie> mFavs) {
            watchedCheck.setVisibility(View.VISIBLE);
            watchedCheck.setChecked(season.visto);
            watchedCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!season.visto) {
                        watchSeason(serie, mFavs, getAdapterPosition());
                    }
                } else {
                    if (season.visto) {
                        unwatchSeason(serie, mFavs, getAdapterPosition());
                    }
                }
            });
        }

        int countEpisodes(Season season) {
            int cont = 0;
            for (Episode e : season.episodes) {
                if (e.visto) {
                    cont++;
                }
            }
            return cont;
        }

        private boolean checkSeasonFinished(SerieResponse.Serie serie) {
            for (Season s : serie.seasons) {
                if (!s.visto) return false;
            }
            return true;
        }

        private void watchSeason(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int temporada) {
            int pos = serie.getPosition(favs);
            Season.sortSeason(favs.get(pos).seasons);

//            MARCAR TEMPORADA
            favs.get(pos).seasons.get(temporada).visto = true;
            favs.get(pos).seasons.get(temporada).watchedDate = new Date();

//            MARCAR EPISODIOS
            for (Episode e : favs.get(pos).seasons.get(temporada).episodes) {
                e.visto = true;
                e.watchedDate = new Date();
            }

//            MARCAR SERIE
            if (checkSeasonFinished(favs.get(pos))) {
                favs.get(pos).finished = true;
                favs.get(pos).finishDate = new Date();
            } else {
                favs.get(pos).finished = false;
                favs.get(pos).finishDate = null;
            }

//            GUARDAR FAVORITOS EN FIREBASE
            FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(favs);
        }

        private void unwatchSeason(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int temporada) {
            int pos = serie.getPosition(favs);
            Season.sortSeason(favs.get(pos).seasons);

//            UNWATCH TEMPORADA
            favs.get(pos).seasons.get(temporada).visto = false;
            favs.get(pos).seasons.get(temporada).watchedDate = null;

//            UNWATCH EPISODIOS
            for (Episode e : favs.get(pos).seasons.get(temporada).episodes) {
                e.visto = false;
                e.watchedDate = null;

            }

//            UNWATCH SERIE
            favs.get(pos).finished = false;
            favs.get(pos).finishDate = null;

            FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(favs);
        }
    }
}
