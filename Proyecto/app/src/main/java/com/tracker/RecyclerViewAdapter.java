package com.tracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tracker.models.SerieTrendingResponse;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.tracker.util.Constants.BASE_URL_IMAGES;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private List<SerieTrendingResponse.SerieTrending> mPelis;
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> mImageUrls) {
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
        this.mContext = mContext;
    }

    public RecyclerViewAdapter(Context mContext, List<SerieTrendingResponse.SerieTrending> pelis) {
        this.mPelis = pelis;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Picasso.get()
                .load(BASE_URL_IMAGES + mPelis.get(position).poster_path)
                .placeholder(R.drawable.ic_launcher_background)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .noFade()
                .into(holder.image);

//        holder.name.setText(mNames.get(position));
        holder.name.setText(mPelis.get(position).name);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image: " + mPelis.get(position).name);
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPelis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.posterTrend);
            name=itemView.findViewById(R.id.titleTrend);
        }
    }
}
