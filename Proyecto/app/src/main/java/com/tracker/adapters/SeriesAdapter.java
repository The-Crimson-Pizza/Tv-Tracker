package com.tracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tracker.R;
import com.tracker.models.SerieTrendingResponse;

import java.util.List;

import static com.tracker.util.Constants.BASE_URL_IMAGES;

public class SeriesAdapter extends BaseAdapter {

    private Context mContext;
    private int layout;
    private List<SerieTrendingResponse.SerieTrending> mSeries;

    public SeriesAdapter(Context mContext, int layout, List<SerieTrendingResponse.SerieTrending> mSeries) {
        this.mContext = mContext;
        this.layout = layout;
        this.mSeries = mSeries;
    }

    @Override
    public int getCount() {
        return mSeries.size();
    }

    @Override
    public Object getItem(int i) {
        return mSeries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<SerieTrendingResponse.SerieTrending> getItemList() {
        return mSeries;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.lista_series_trending, null);
        } else {
            v = convertView;
        }

        TextView title = v.findViewById(R.id.titleTrend);
        ImageView image = v.findViewById(R.id.posterTrend);

        Picasso.get()
                .load(BASE_URL_IMAGES + mSeries.get(i).poster_path)
                .placeholder(R.drawable.ic_launcher_background)
                .noFade()
                .into(image);
        title.setText(mSeries.get(i).name);

        return v;
    }
}
