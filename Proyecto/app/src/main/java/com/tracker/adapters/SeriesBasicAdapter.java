package com.tracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.models.SerieBasicResponse;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES;
import static com.tracker.util.Constants.ID_SERIE;

public class SeriesBasicAdapter extends RecyclerView.Adapter<SeriesBasicAdapter.ViewHolder> {

    private List<SerieBasicResponse.SerieBasic> mSeries;
    private Context mContext;
    private FragmentManager fManager;

    public SeriesBasicAdapter(Context mContext, List<SerieBasicResponse.SerieBasic> series, FragmentManager fManager) {
        this.mSeries = series;
        this.mContext = mContext;
        this.fManager = fManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get()
                .load(BASE_URL_IMAGES + mSeries.get(position).poster_path)
                .placeholder(R.drawable.default_poster)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .noFade()
                .into(holder.image);
        holder.name.setText(mSeries.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mSeries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterTrend);
            name = itemView.findViewById(R.id.titleTrend);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_SERIE, mSeries.get(pos).id);
                Navigation.findNavController(v).navigate(R.id.action_home_to_series, bundle);
            });
        }

    }
}
