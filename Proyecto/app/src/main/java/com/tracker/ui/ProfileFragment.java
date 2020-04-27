package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.models.serie.SerieResponse;
import com.tracker.repositories.FirebaseDb;
import com.tracker.util.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private Context mContext;
    private PieChart mPieChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getFollowingSeries(view);

        mPieChart = view.findViewById(R.id.chart);
        mPieChart.notifyDataSetChanged();
        initGenres();


    }

    private void getFollowingSeries(View view) {
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                mFavs.addAll(Objects.requireNonNull(dataSnapshot.getValue(new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                })));

                initGenres();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                initGenres();
            }
        });
    }


    private void initGenres() {
        List<PieEntry> entries = Stats.getInstance(mFavs, mContext).getPieGenres();
        PieDataSet dataSet = new PieDataSet(entries, "Top Genres"); // add entries to dataset
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData lineData = new PieData(dataSet);

        mPieChart.setUsePercentValues(true);
        mPieChart.getLegend().setEnabled(false);

        mPieChart.setData(lineData);
        mPieChart.notifyDataSetChanged();
        mPieChart.invalidate(); // refresh
    }
}
