package com.tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.models.SerieTrendingResponse;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES;

public class SeriesTrendingAdapter extends RecyclerView.Adapter<SeriesTrendingAdapter.CustomRecyclerView> {

    private List<SerieTrendingResponse.SerieTrending> itemList;
    private OnTrendingListener mOnTrendingListener;

    public SeriesTrendingAdapter(Context context, List<SerieTrendingResponse.SerieTrending> itemList, OnTrendingListener onTrendingListener) {
        this.itemList = itemList;
        this.mOnTrendingListener = onTrendingListener;
    }

    @Override
    public CustomRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_trending, null);
        CustomRecyclerView rcv = new CustomRecyclerView(layoutView, mOnTrendingListener);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesTrendingAdapter.CustomRecyclerView holder, int position) {
        SerieTrendingResponse.SerieTrending myData = itemList.get(position);
        holder.txtLabel.setText(myData.name);
        Picasso.get()
                .load(BASE_URL_IMAGES+myData.poster_path)
                //.placeholder(R.drawable.ic_launcher_background)
                .noFade()
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class CustomRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtLabel;
        ImageView avatar;
        OnTrendingListener onTrendingListener;

        CustomRecyclerView(View itemView, OnTrendingListener onTrendingListener) {
            super(itemView);
            txtLabel = itemView.findViewById(R.id.titleTrend);
            avatar = itemView.findViewById(R.id.posterTrend);
            this.onTrendingListener = onTrendingListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTrendingListener.onClickTrending(getAdapterPosition());
        }
    }

    public List<SerieTrendingResponse.SerieTrending> getItemList(){
        return itemList;
    }

    public interface OnTrendingListener{
        void onClickTrending(int position);
    }
}
