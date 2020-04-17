package com.tracker.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.models.SerieFav;
import com.tracker.models.seasons.Episode;
import com.tracker.models.seasons.Season;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

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
        ProgressBar favProgress;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            favPoster = itemView.findViewById(R.id.posterBasic);
            favName = itemView.findViewById(R.id.serie_name);
            favStatus = itemView.findViewById(R.id.episode_fecha);
            favProgress = itemView.findViewById(R.id.progreso);
            favVistos = itemView.findViewById(R.id.vistos);

            itemView.setOnClickListener(v -> {
//                int pos = getAdapterPosition();
//                Bundle bundle = new Bundle();
//                bundle.putInt(ID_SEASON, id);
//                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static FavoritesAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_series_following, parent, false);
            return new FavoritesAdapter.ViewHolder(view);
        }

        void bindTo(SerieFav favSerie) {
            if (favSerie != null) {
//                id = favSerie.seasonNumber;
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

            }
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
    }
}
