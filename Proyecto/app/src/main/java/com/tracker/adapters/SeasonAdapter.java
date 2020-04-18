package com.tracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.tracker.R;
import com.tracker.data.FirebaseDb;
import com.tracker.models.SerieFav;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Util;

import java.util.Collections;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_SEASON;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private List<Season> mSeasons;
    private SerieResponse.Serie mSerie;
    private List<SerieFav> mFavs;
    private static Context mContext;

    public SeasonAdapter(Context mContext, SerieResponse.Serie serie, List<SerieFav> favs) {
        this.mFavs = favs;
        this.mSerie = serie;
        this.mSeasons = serie.seasons;
        this.mContext = mContext;
        if (mSeasons != null) {
            ViewHolder.sortSeason(mSeasons);
        }
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SeasonAdapter.ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mSeasons.get(position), mSerie, mFavs);
    }

    @Override
    public int getItemCount() {
        if (mSeasons != null) {
            return mSeasons.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView seasonPoster;
        TextView seasonName;
        TextView numEpisodes;
        MaterialCheckBox watchedCheck;

        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonPoster = itemView.findViewById(R.id.image_season);
            seasonName = itemView.findViewById(R.id.season_name);
            numEpisodes = itemView.findViewById(R.id.episode_number);
            watchedCheck = itemView.findViewById(R.id.checkbox_watched);

            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SEASON, id);
                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static SeasonAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_season, parent, false);
            return new SeasonAdapter.ViewHolder(view);
        }

        void bindTo(Season season, SerieResponse.Serie serie, List<SerieFav> mFavs) {
            if (season != null) {
                id = season.seasonNumber;
                seasonName.setText(season.name);
                String capis = mContext.getString(R.string.n_episodes, season.episodeCount);
                numEpisodes.setText(capis);
                Util.getImage(BASE_URL_IMAGES_POSTER + season.posterPath, seasonPoster, mContext);

                if (serie.isFav) {
                    watchedCheck.setVisibility(View.VISIBLE);
                    watchedCheck.setChecked(season.visto);
                    watchedCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            addSeen(season, serie, mFavs, getAdapterPosition());
                        } else {
                            // todo-borrar
                        }
                    });

                }
            }
        }

        private void addSeen(Season season, SerieResponse.Serie serie, List<SerieFav> favs, int temporada) {
//        todo - añadir campo fecha terminado y añadido a temporadas y episodios
//            todo - checkear favs en episodios y season
            int pos = SerieFav.getPosition(serie, favs);
            SerieFav fav = favs.get(pos);

            sortSeason(fav.seasons);

            fav.seasons.get(pos).visto = true;
            for (Episode e : fav.seasons.get(pos).episodes) {
                e.visto = true;
            }
            FirebaseDb.getInstance().setSeriesFav(favs);

        }

        private static void sortSeason(List<Season> seasons) {
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
