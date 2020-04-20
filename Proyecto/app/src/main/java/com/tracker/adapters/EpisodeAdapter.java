package com.tracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.tracker.R;
import com.tracker.data.FirebaseDb;
import com.tracker.models.seasons.Episode;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.FORMAT_LONG;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private Context mContext;
    private List<Episode> mEpisodes;
    private SerieResponse.Serie mSerie;
    private List<SerieResponse.Serie> mFavs;
    private int mSeasonPosition;

    public EpisodeAdapter(Context mContext, List<Episode> episodes, SerieResponse.Serie serie,
                          List<SerieResponse.Serie> favs, int seasonPos) {
        this.mFavs = favs;
        this.mSerie = serie;
        this.mEpisodes = episodes;
        this.mContext = mContext;
        this.mSeasonPosition = seasonPos;
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EpisodeAdapter.ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mEpisodes.get(position), mSerie, mFavs, mSeasonPosition, mContext);
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
        TextView episodeNameExpandable;
        TextView episodeDate;
        TextView episodeOverview;
        TextView episodeTime;
        MaterialCheckBox watchedCheck;

        ConstraintLayout expandableView;
        Button arrowBtn;
        LinearLayout cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeBackdrop = itemView.findViewById(R.id.image_episode);
            episodeName = itemView.findViewById(R.id.episode_name);
            episodeDate = itemView.findViewById(R.id.episode_fecha);
            episodeOverview = itemView.findViewById(R.id.episode_sinopsis);
            episodeTime = itemView.findViewById(R.id.episode_time);
            watchedCheck = itemView.findViewById(R.id.checkbox_watched);
            episodeNameExpandable = itemView.findViewById(R.id.next_episode);

            // CARDVIEW INTERACTION
            expandableView = itemView.findViewById(R.id.expandableViewEpi);
            arrowBtn = itemView.findViewById(R.id.arrowBtnEpi);
            cardView = itemView.findViewById(R.id.cardview);
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

        static EpisodeAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_episodes, parent, false);
            return new EpisodeAdapter.ViewHolder(view);
        }

        void bindTo(Episode episode, SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int mSeasonPos, Context c) {
            if (episode != null) {
                setWatchedCheck(episode, serie, favs, mSeasonPos);

                episodeName.setText(episode.name);
                episodeNameExpandable.setText(episode.name);
                episodeDate.setText(Util.getFecha(episode.airDate, FORMAT_LONG));
                episodeOverview.setText(episode.overview);
                Util.getImage(BASE_URL_IMAGES_POSTER + episode.stillPath, episodeBackdrop, c);

                // RUNTIME
                if (!serie.episodeRunTime.isEmpty()) {
                    episodeTime.setText(getMinutos(serie.episodeRunTime.get(0)));
                } else {
                    episodeTime.setText(getMinutos(0));
                }
            }
        }

        /**
         * Checks if the episode is watched and sets it true or false the CheckBox
         *
         * @param episode   episode of the season
         * @param serie     serie loaded in the SerieFragment
         * @param favs      list of series in the following list
         * @param seasonPos position of the season in the season list
         */
        private void setWatchedCheck(Episode episode, SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int seasonPos) {
            if (serie.added) {
                watchedCheck.setVisibility(View.VISIBLE);
                watchedCheck.setChecked(episode.visto);
                watchedCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        if (!episode.visto) {
                            watchEpisode(serie, favs, getAdapterPosition(), seasonPos);
                        }
                    } else {
                        if (episode.visto) {
                            unwatchEpisode(serie, favs, getAdapterPosition(), seasonPos);
                        }
                    }
                });
            }
        }

        /**
         * Set as watched the episode in the RecyclerView
         *
         * @param serie      serie loaded in the SerieFragment
         * @param favs       list of series in the following list
         * @param episodePos episode position in the RecyclerView
         * @param seasonPos  position of the season in the season list
         */
        private void watchEpisode(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int episodePos, int seasonPos) {
            int favPosition = serie.getPosition(favs);
            if (favPosition != -1) {
                favs.get(favPosition)
                        .seasons.get(seasonPos)
                        .episodes.get(episodePos)
                        .visto = true;
                favs.get(favPosition)
                        .seasons.get(seasonPos)
                        .episodes.get(episodePos)
                        .watchedDate = new Date();

                FirebaseDb.getInstance().setSeriesFav(favs);
            }
        }

        /**
         * Set as unwatched the episode in the RecyclerView
         *
         * @param serie      serie loaded in the SerieFragment
         * @param favs       list of series in the following list
         * @param episodePos episode position in the RecyclerView
         * @param seasonPos  position of the season in the season list
         */
        private void unwatchEpisode(SerieResponse.Serie serie, List<SerieResponse.Serie> favs, int episodePos, int seasonPos) {
            int favPosition = serie.getPosition(favs);
            if (favPosition != -1) {
                favs.get(favPosition)
                        .seasons.get(seasonPos)
                        .episodes.get(episodePos)
                        .visto = false;
                favs.get(favPosition)
                        .seasons.get(seasonPos)
                        .episodes.get(episodePos)
                        .watchedDate = null;

                FirebaseDb.getInstance().setSeriesFav(favs);
            }
        }

        /**
         * Turns minutes to mm:ss format
         *
         * @param minutes minutes to convert format
         * @return a string with the mm:ss format
         */
        private String getMinutos(int minutes) {
            return String.format(Locale.getDefault(), Constants.FORMAT_MINUTES, minutes, 0);
        }


    }
}
