package com.tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.models.SerieBasicResponse;
import com.tracker.ui.DetallesSerieFragment;
import com.tracker.ui.ProfileFragment;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.tracker.util.Constants.BASE_URL_IMAGES;

public class SeriesBasicAdapter extends RecyclerView.Adapter<SeriesBasicAdapter.ViewHolder> {

    private List<SerieBasicResponse.SerieBasic> mPelis;
    private Context mContext;
    private FragmentManager fManager;

    public SeriesBasicAdapter(Context mContext, List<SerieBasicResponse.SerieBasic> pelis, FragmentManager fManager) {
        this.mPelis = pelis;
        this.mContext = mContext;
        this.fManager = fManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Picasso.get()
                .load(BASE_URL_IMAGES + mPelis.get(position).poster_path)
                .placeholder(R.drawable.default_poster)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .noFade()
                .into(holder.image);
        holder.name.setText(mPelis.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mPelis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterTrend);
            name = itemView.findViewById(R.id.titleTrend);
            itemView.setOnClickListener(v -> fManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.nav_host_fragment, new DetallesSerieFragment())
                    .commit());
        }

    }
}
