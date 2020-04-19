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
import com.tracker.models.actor.PersonResponse;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_ACTOR;
import static com.tracker.util.Constants.ID_SERIE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SerieResponse.Serie> mSeries;
    private List<PersonResponse.Person> mActores;

    private static Context mContext;
    private boolean isSerie;


    public SearchAdapter(Context mContext, List<SerieResponse.Serie> series, List<PersonResponse.Person> actores, boolean isSerie) {
        this.mSeries = series;
        this.mActores = actores;
        this.isSerie = isSerie;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, isSerie);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (isSerie) {
            holder.bindTo(mSeries.get(position));
        } else {
            holder.bindTo(mActores.get(position));
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

        ImageView image;
        TextView name;
        TextView rating;
        int id;
        private static boolean isSerie;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            rating = itemView.findViewById(R.id.valoration);

            if (isSerie) {
                itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_SERIE, id);
                    Navigation.findNavController(v).navigate(R.id.action_search_to_series, bundle);
                });
            } else {
                itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_ACTOR, id);
                    Navigation.findNavController(v).navigate(R.id.action_search_to_actores, bundle);
                });
            }
        }

        static ViewHolder create(ViewGroup parent, boolean serie) {
            isSerie = serie;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic_search, parent, false);
            return new ViewHolder(view);
        }

        void bindTo(SerieResponse.Serie serie) {
            if (serie != null) {
                id = serie.id;
                name.setText(serie.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serie.posterPath, image, mContext);
                //rating.setText(String.valueOf(serie.voteAverage));
            }
        }

        void bindTo(PersonResponse.Person person) {
            if (person != null) {
                id = person.id;
                name.setText(person.name);
                Util.getImagePortrait(BASE_URL_IMAGES_POSTER + person.profilePath, image, mContext);
                //rating.setVisibility(View.GONE);
            }
        }
    }
}
