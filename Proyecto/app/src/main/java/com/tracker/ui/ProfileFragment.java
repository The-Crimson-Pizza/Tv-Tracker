package com.tracker.ui;

import android.content.Context;
import android.graphics.Color;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
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
    private static final int[] COLORS = {
            Color.parseColor("#48c9b0"),
            Color.parseColor("#2DAEA9"),
            Color.parseColor("#23949C"),
            Color.parseColor("#28798A"),
            Color.parseColor("#2E6072"),
            Color.parseColor("#2F4858"),
            Color.parseColor("#00B7C3"),
            Color.parseColor("#00A2D2"),
            Color.parseColor("#0089D5"),
            Color.parseColor("#446BC5"),
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getFollowingSeries(view);

        mPieChart = view.findViewById(R.id.chart);
//        mPieChart.notifyDataSetChanged();
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
        List<PieEntry> entries = Stats.getInstance(mContext).getPieGenres(mFavs);
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(COLORS);
        dataSet.setValueTextSize(15);
        dataSet.setValueTextColor(Color.WHITE);

        PieData lineData = new PieData(dataSet);

        lineData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });

        mPieChart.setDrawEntryLabels(false);
        mPieChart.setEntryLabelTextSize(15);
        mPieChart.getLegend().setTextSize(17);
//        mPieChart.getLegend().setTextColor(Color.parseColor("#48c9b0"));
        mPieChart.getLegend().setTextColor(Color.WHITE);
        mPieChart.getLegend().setWordWrapEnabled(true);
        mPieChart.getDescription().setText("");
        mPieChart.setUsePercentValues(false);
//        mPieChart.setHoleColor(Color.parseColor("#48c9b0"));

        mPieChart.setData(lineData);
        mPieChart.notifyDataSetChanged();
        mPieChart.invalidate(); // refresh
    }
}
