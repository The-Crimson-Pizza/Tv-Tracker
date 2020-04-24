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
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

public class NetworkGenreAdapter extends RecyclerView.Adapter<NetworkGenreAdapter.ViewHolder> {

    private List<BasicResponse.SerieBasic> mSeries;
    private Context mContext;
    private boolean isGenre;

    public NetworkGenreAdapter(Context mContext, List<BasicResponse.SerieBasic> series, boolean genre) {
        this.isGenre = genre;
        this.mSeries = series;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getItemCount() > 0) {
            holder.bindTo(mSeries.get(position), isGenre, mContext);
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
        TextView rating;
        int id;
        boolean isGenre;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            rating = itemView.findViewById(R.id.valoration);

            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID_SERIE, id);
                Navigation.findNavController(v).navigate(R.id.action_global_navigation_series, bundle);
            });


        }

        static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
            return new ViewHolder(view);
        }

        void bindTo(BasicResponse.SerieBasic serieBasic, boolean genre, Context context) {
            isGenre = genre;
            if (serieBasic != null) {
                id = serieBasic.id;
                name.setText(serieBasic.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serieBasic.poster_path, image, context);
                rating.setText(String.valueOf(serieBasic.voteAverage));
            }
        }
    }
}