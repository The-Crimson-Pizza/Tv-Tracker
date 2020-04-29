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
import com.tracker.models.actor.PersonResponse;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

/**
 * Adapter for the RecyclerView that hosts the info of the searched shows or actors
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final List<SerieResponse.Serie> mSeries;
    private final List<PersonResponse.Person> mActores;
    private final Context mContext;
    private final boolean isSerie;

    public SearchAdapter(Context mContext, List<SerieResponse.Serie> series, List<PersonResponse.Person> actores, boolean isSerie) {
        this.mSeries = series;
        this.mActores = actores;
        this.isSerie = isSerie;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (isSerie) {
            holder.bindTo(mSeries.get(position), mContext);
        } else {
            holder.bindTo(mActores.get(position), mContext);
        }
    }

    @Override
    public int getItemCount() {
        if (isSerie) {
            if (mSeries != null) {
                return mSeries.size();
            }
        } else {
            if (mActores != null) {
                return mActores.size();
            }
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView image;
        final TextView name;
        final TextView rating;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            rating = itemView.findViewById(R.id.ratingBasic);
            rating.setVisibility(View.GONE);
        }

        static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
            return new ViewHolder(view);
        }

        void bindTo(SerieResponse.Serie serie, Context context) {
            if (serie != null) {
                id = serie.id;
                name.setText(serie.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serie.posterPath, image, context);
                itemView.setOnClickListener(v -> goToFragment(v, Constants.ID_SERIE, R.id.action_search_to_series));
            }
        }

        void bindTo(PersonResponse.Person person, Context context) {
            if (person != null) {
                id = person.id;
                name.setText(person.name);
                Util.getImagePortrait(BASE_URL_IMAGES_POSTER + person.profilePath, image, context);
                itemView.setOnClickListener(v -> goToFragment(v, Constants.ID_ACTOR, R.id.action_search_to_actores));
            }
        }

        private void goToFragment(View v, String id, int action) {
            Bundle bundle = new Bundle();
            bundle.putInt(id, this.id);
            Navigation.findNavController(v).navigate(action, bundle);
        }
    }
}
