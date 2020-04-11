package com.tracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.models.seasons.Episode;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private List<Episode> mEpisodes;
    private static Context mContext;

    public EpisodeAdapter(Context mContext, List<Episode> episodes) {
        this.mEpisodes = episodes;
        this.mContext = mContext;
//        if(mEpisodes !=null){
//            new Util().ordenarTemporadas(mEpisodes);
//        }
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EpisodeAdapter.ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mEpisodes.get(position));
    }

    @Override
    public int getItemCount() {
        if (mEpisodes != null) {
            return mEpisodes.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView fecha;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_episode);
            name = itemView.findViewById(R.id.episode_name);
            fecha = itemView.findViewById(R.id.episode_fecha);

            itemView.setOnClickListener(v -> {
//                int pos = getAdapterPosition();
//                Bundle bundle = new Bundle();
//                bundle.putInt(ID_SEASON, pos);
//                Navigation.findNavController(v).navigate(R.id.action_series_to_episodes, bundle);
            });
        }

        static EpisodeAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_episodes, parent, false);
            return new EpisodeAdapter.ViewHolder(view);
        }

        void bindTo(Episode episode) {
            if (episode != null) {
                id = episode.id;
                name.setText(episode.name);
                fecha.setText(episode.airDate);
                // todo - probablemente hay que cambiar la base_url
                new Util().getImage(BASE_URL_IMAGES_POSTER + episode.stillPath, image, mContext);
            }
        }


    }
}
