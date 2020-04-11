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
import com.tracker.models.BasicResponse;
import com.tracker.models.seasons.Season;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_SEASON;
import static com.tracker.util.Constants.ID_SERIE;

public class SeasonAdapter  extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private List<Season> mSeasons;
    private static Context mContext;

    public SeasonAdapter(Context mContext, List<Season> seasons) {
        this.mSeasons = seasons;
        this.mContext = mContext;
        if(mSeasons !=null){
            new Util().ordenarTemporadas(mSeasons);
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

        ImageView image;
        TextView name;
        TextView episodes;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_season);
            name = itemView.findViewById(R.id.season_name);
            episodes = itemView.findViewById(R.id.episode_number);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SEASON, pos);
                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static SeasonAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_season, parent, false);
            return new SeasonAdapter.ViewHolder(view);
        }

        void bindTo(Season season) {
            if (season != null) {
                id = season.id;
                name.setText(season.name);
                episodes.setText(mContext.getString(R.string.n_episodes, season.episodes.size()));
                new Util().getImage(BASE_URL_IMAGES_POSTER + season.posterPath, image, mContext);
            }
        }


    }
}
