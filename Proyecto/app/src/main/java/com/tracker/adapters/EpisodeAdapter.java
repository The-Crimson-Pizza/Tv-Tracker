package com.tracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.tracker.R;
import com.tracker.models.seasons.Episode;
import com.tracker.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.FORMAT_LONG;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private List<Episode> mEpisodes;
    private static Context mContext;
    private int mRuntime;

    public EpisodeAdapter(Context mContext, List<Episode> episodes, int runtime) {
        this.mEpisodes = episodes;
        this.mContext = mContext;
        this.mRuntime = runtime;
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
        holder.bindTo(mEpisodes.get(position), mRuntime);
    }

    @Override
    public int getItemCount() {
        if (mEpisodes != null) {
            return mEpisodes.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView episodeBackdrop;
        TextView episodeName;
        TextView episodeDate;
        TextView episodeOverview;
        TextView episodeTime;
        MaterialCheckBox watchedCheck;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeBackdrop = itemView.findViewById(R.id.image_episode);
            episodeName = itemView.findViewById(R.id.episode_name);
            episodeDate = itemView.findViewById(R.id.episode_fecha);
            episodeOverview = itemView.findViewById(R.id.episode_sinopsis);
            episodeTime = itemView.findViewById(R.id.episode_time);
            watchedCheck = itemView.findViewById(R.id.checkbox_watched);
        }

        static EpisodeAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_episodes, parent, false);
            return new EpisodeAdapter.ViewHolder(view);
        }

        void bindTo(Episode episode, int runtime) {
            if (episode != null) {
                episodeName.setText(episode.name);
                episodeDate.setText(Util.getFecha(episode.airDate, FORMAT_LONG));
                episodeOverview.setText(episode.overview);
                episodeTime.setText(getMinutos(runtime));
                Util.getImage(BASE_URL_IMAGES_POSTER + episode.stillPath, episodeBackdrop, mContext);


                watchedCheck.setChecked(episode.visto);
            }
        }

        private String getMinutos(int minutes) {
            return String.format(Locale.getDefault(), "%d:%02d", minutes, 0);

        }


    }
}
