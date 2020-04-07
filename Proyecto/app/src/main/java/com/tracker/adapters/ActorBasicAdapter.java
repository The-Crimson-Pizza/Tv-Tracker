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
import com.tracker.models.series.Credits;
import com.tracker.models.series.Serie;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_PORTRAIT;
import static com.tracker.util.Constants.ID_ACTOR;

public class ActorBasicAdapter extends RecyclerView.Adapter<ActorBasicAdapter.ViewHolder> {

    private List<Credits.Cast> mCastSerie;
    private static Context mContext;

    public ActorBasicAdapter(Context context, Serie serie) {
        this.mCastSerie = serie.credits.cast;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorBasicAdapter.ViewHolder holder, final int position) {
        holder.bindTo(mCastSerie.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCastSerie != null) {
            return mCastSerie.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView character;
        int id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.actor_name);
            character = itemView.findViewById(R.id.character_name);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_ACTOR, id);
                Navigation.findNavController(v).navigate(R.id.action_series_to_actores, bundle);
            });
        }

        static public ActorBasicAdapter.ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cast_vertical, parent, false);
            return new ActorBasicAdapter.ViewHolder(view);
        }

        public void bindTo(Credits.Cast cast) {
            if (cast != null) {
                name.setText(cast.name);
                character.setText(cast.character);
                new Util().getImageNoPlaceholder(BASE_URL_IMAGES_PORTRAIT + cast.profilePath, image, mContext);
                id = cast.id;
            }
        }
    }
}
