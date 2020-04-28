package com.tracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.tracker.models.BasicResponse;
import com.tracker.util.Constants;
import com.tracker.util.Util;

import java.util.List;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;
import static com.tracker.util.Constants.BASE_URL_IMAGES_POSTER;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<BasicResponse.SerieBasic> mSeries;
    private Context mContext;

    public HomeAdapter(Context mContext, List<BasicResponse.SerieBasic> series) {
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
            holder.bindTo(mSeries.get(position), mContext);
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            rating = itemView.findViewById(R.id.valoration);

            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID_SERIE, id);
                if (Util.isNetworkAvailable(itemView.getContext())) {
                    Navigation.findNavController(v).navigate(R.id.action_home_to_series, bundle);
                } else {
                    Snackbar.make(v, itemView.getContext().getString(R.string.no_conn), LENGTH_LONG)
                            .setAction(R.string.activate_net, v1 -> itemView.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
                }
            });
        }

        static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
            return new ViewHolder(view);
        }

        void bindTo(BasicResponse.SerieBasic serieBasic, Context context) {
            if (serieBasic != null) {
                id = serieBasic.id;
                name.setText(serieBasic.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serieBasic.poster_path, image, context);
                rating.setText(String.valueOf(serieBasic.voteAverage));
            }
        }
    }
}
