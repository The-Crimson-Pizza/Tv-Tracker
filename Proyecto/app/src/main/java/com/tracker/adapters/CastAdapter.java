package com.tracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;
import com.tracker.models.people.MovieCredits;
import com.tracker.models.people.TvCredits;
import com.tracker.ui.WebViewActivity;
import com.tracker.util.Util;

import java.util.Collections;
import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.BASE_URL_WEB;
import static com.tracker.util.Constants.ID_SERIE;
import static com.tracker.util.Constants.URL_WEBVIEW;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<TvCredits.Cast> mSeries;
    private List<MovieCredits.Cast> mMovies;
    private Context mContext;
    private boolean isMovie = false;

    CastAdapter(Context mContext, List<MovieCredits.Cast> movies, boolean movie) {
        this.mMovies = movies;
        this.mContext = mContext;
        isMovie = true;
        if (!mMovies.isEmpty()) {
            sortFilms(mMovies);
        }
    }

    CastAdapter(Context mContext, List<TvCredits.Cast> tv) {
        this.mSeries = tv;
        this.mContext = mContext;
        if (!mSeries.isEmpty()) {
            sortSeries(mSeries);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (isMovie) {
            holder.name.setText(mMovies.get(position).title);
            holder.character.setText(mMovies.get(position).character);
            if (mMovies.get(position).releaseDate != null) {
                holder.fecha.setText(mMovies.get(position).releaseDate.split("-")[0]);
            } else {
                holder.fecha.setText("");
            }

            Util.getImage(BASE_URL_IMAGES_POSTER + mMovies.get(position).posterPath, holder.image, mContext);

            holder.itemView.setOnClickListener(v -> Snackbar.make(v, "Not yet implemented", Snackbar.LENGTH_LONG)
                    .setAction("Open in web", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra(URL_WEBVIEW, BASE_URL_WEB + mMovies.get(position).id));
                        }
                    }).show());
        } else {
            holder.name.setText(mSeries.get(position).name);
            holder.character.setText(mSeries.get(position).character);
            if (mSeries.get(position).firstAirDate != null) {
                holder.fecha.setText(mSeries.get(position).firstAirDate.split("-")[0]);
            } else {
                holder.fecha.setText("");
            }
            Util.getImage(BASE_URL_IMAGES_POSTER + mSeries.get(position).posterPath, holder.image, mContext);

            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SERIE, mSeries.get(position).id);
                Navigation.findNavController(v).navigate(R.id.action_actores_to_series, bundle);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isMovie) {
            if (mMovies != null) {
                return mMovies.size();
            }
        } else {
            if (mSeries != null) {
                return mSeries.size();
            }
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView character;
        TextView fecha;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posters);
            name = itemView.findViewById(R.id.title);
            character = itemView.findViewById(R.id.name_character);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }

    private void sortSeries(List<TvCredits.Cast> series) {
        Collections.sort(series, (serie1, serie2) -> {
            String fecha1 = serie1.firstAirDate;
            String fecha2 = serie2.firstAirDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(serie1.id);
                String id2 = String.valueOf(serie2.id);
                return id2.compareTo(id1);
            }
        });
    }

    private void sortFilms(List<MovieCredits.Cast> films) {
        Collections.sort(films, (film1, film2) -> {
            String fecha1 = film1.releaseDate;
            String fecha2 = film2.releaseDate;
            if (fecha1 != null && fecha2 != null) {
                return fecha2.compareTo(fecha1);
            } else {
                String id1 = String.valueOf(film1.id);
                String id2 = String.valueOf(film2.id);
                return id2.compareTo(id1);
            }
        });
    }
}
