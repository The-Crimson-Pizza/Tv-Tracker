package com.tracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.tracker.util.Constants.ID_ACTOR;

public class ActorBasicAdapter extends RecyclerView.Adapter<ActorBasicAdapter.ViewHolder> {

    private List<Credits.Cast> casting;
    private Context mContext;

    public ActorBasicAdapter(Context mContext, Serie serie) {
        casting = serie.credits.cast;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cast_vertical, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorBasicAdapter.ViewHolder holder, final int position) {

        holder.name.setText(casting.get(position).name);
        holder.character.setText(casting.get(position).character);
        new Util().usePicasso(BASE_URL_IMAGES_POSTER + casting.get(position).profilePath, holder.image);

    }

    @Override
    public int getItemCount() {
        return casting.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView character;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.actor_name);
            character = itemView.findViewById(R.id.character_name);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Log.d("HOLA", casting.get(pos).name);
                Bundle bundle = new Bundle();
                bundle.putInt(ID_ACTOR, casting.get(pos).id);
//                Navigation.findNavController(v).navigate(R.id.action_home_to_series, bundle);
            });
        }

    }
}
