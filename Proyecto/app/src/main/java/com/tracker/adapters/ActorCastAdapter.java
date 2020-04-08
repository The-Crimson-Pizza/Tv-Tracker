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
import com.tracker.models.people.Person;
import com.tracker.models.people.TvCredits;
import com.tracker.ui.WebView;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.BASE_URL_WEB;
import static com.tracker.util.Constants.ID_SERIE;
import static com.tracker.util.Constants.URL_WEBVIEW;

public class ActorCastAdapter extends RecyclerView.Adapter<ActorCastAdapter.ViewHolder> {

    private List<TvCredits.Cast> mSeries;
    private List<MovieCredits.Cast> mPeliculas;
    private Context mContext;
    private boolean isMovie;

    public ActorCastAdapter(Context mContext, Person actor, boolean movie) {
        this.mSeries = actor.tvCredits.cast;
        this.mPeliculas = actor.movieCredits.cast;
        this.mContext = mContext;
        isMovie = movie;
        // TODO Ordenar arrays por fecha
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
            holder.name.setText(mPeliculas.get(position).title);
            holder.character.setText(mPeliculas.get(position).character);
            new Util().getImage(BASE_URL_IMAGES_POSTER + mPeliculas.get(position).posterPath, holder.image, mContext);
            holder.itemView.setOnClickListener(v -> {
                Snackbar.make(v, "Not yet implemented", Snackbar.LENGTH_LONG)
                        .setAction("Open in web", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, WebView.class);
                                intent.putExtra(URL_WEBVIEW, BASE_URL_WEB+mPeliculas.get(position).id);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }
                        }).show();
//                Bundle bundle = new Bundle();
//                bundle.putInt(ID_SERIE, mSeries.get(position).id);
            });


        } else {
            holder.name.setText(mSeries.get(position).name);
            holder.character.setText(mSeries.get(position).character);
            new Util().getImage(BASE_URL_IMAGES_POSTER + mSeries.get(position).posterPath, holder.image, mContext);


        }

    }

    @Override
    public int getItemCount() {
        if (mSeries != null) {
            return mSeries.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView character;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posters);
            name = itemView.findViewById(R.id.title);
            character = itemView.findViewById(R.id.name_character);
//            if (!isMovie) {
//                itemView.setOnClickListener(v -> {
//                    int pos = getAdapterPosition();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(ID_SERIE, pos);
//                    Navigation.findNavController(v).navigate(R.id.action_home_to_series, bundle);
//                });
//            }
        }
    }
}
