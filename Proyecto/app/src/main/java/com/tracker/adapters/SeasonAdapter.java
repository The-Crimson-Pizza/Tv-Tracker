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

import com.tracker.R;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Util;

import java.util.Collections;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_SEASON;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private List<Season> mSeasons;
    private static Context mContext;

    public SeasonAdapter(Context mContext, SerieResponse.Serie serie) {
        this.mSeasons = serie.seasons;
        this.mContext = mContext;
        if (mSeasons != null) {
            sortSeason(mSeasons);
        }
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SeasonAdapter.ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mSeasons.get(position));
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
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonPoster = itemView.findViewById(R.id.image_season);
            seasonName = itemView.findViewById(R.id.season_name);
            numEpisodes = itemView.findViewById(R.id.episode_number);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SEASON, id);
                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static SeasonAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_season, parent, false);
            return new SeasonAdapter.ViewHolder(view);
        }

        void bindTo(Season season) {
            if (season != null) {
                id = season.seasonNumber;
                seasonName.setText(season.name);
                String capis = mContext.getString(R.string.n_episodes, season.episodeCount);
                numEpisodes.setText(capis);
                Util.getImage(BASE_URL_IMAGES_POSTER + season.posterPath, seasonPoster, mContext);
            }
        }
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
