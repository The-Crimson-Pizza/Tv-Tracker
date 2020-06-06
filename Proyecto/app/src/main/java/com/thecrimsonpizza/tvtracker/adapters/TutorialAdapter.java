package com.thecrimsonpizza.tvtracker.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.models.BasicResponse;
import com.thecrimsonpizza.tvtracker.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_POSTER;
import static com.thecrimsonpizza.tvtracker.util.Constants.FAV_TEMP_DATA;
import static com.thecrimsonpizza.tvtracker.util.Constants.MY_PREFS;

/**
 * Adapter for the tutorial RecyclerView that hosts the basic info of trending shows
 */
public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {

    private final List<BasicResponse.SerieBasic> mSeries;
    private static final List<Integer> mCodes = new ArrayList<>();
    private final Context mContext;

    public TutorialAdapter(Context mContext, List<BasicResponse.SerieBasic> series) {
        this.mSeries = series;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_series_basic_vertical_tutorial, parent, false);
        return new ViewHolder(view, mContext);
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

        final ImageView image;
        final TextView name;
        int id;
        boolean pulsado = false;

        ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            image = itemView.findViewById(R.id.posterBasic);
            name = itemView.findViewById(R.id.titleBasic);
            SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

            itemView.setOnClickListener(view -> {
                if (!pulsado) {
                    mCodes.add(id);
                } else {
                    mCodes.remove(id);
                }
                pulsado = !pulsado;
                setPrefIntArray(prefs, mCodes.stream().mapToInt(i -> i).toArray());
            });
        }

        public void setPrefIntArray(SharedPreferences sp, int[] value) {
            SharedPreferences.Editor prefEditor = sp.edit();
            String s;
            try {
                JSONArray jsonArr = new JSONArray();
                for (int i : value)
                    jsonArr.put(i);

                JSONObject json = new JSONObject();
                json.put(FAV_TEMP_DATA, jsonArr);

                s = json.toString();
            } catch (JSONException excp) {
                s = "";
            }

            prefEditor.putString(FAV_TEMP_DATA, s);
            prefEditor.commit();
        }

        void bindTo(BasicResponse.SerieBasic serieBasic, Context context) {
            if (serieBasic != null) {
                id = serieBasic.id;
                name.setText(serieBasic.name);
                Util.getImage(BASE_URL_IMAGES_POSTER + serieBasic.posterPath, image, context);
            }
        }
    }
}
