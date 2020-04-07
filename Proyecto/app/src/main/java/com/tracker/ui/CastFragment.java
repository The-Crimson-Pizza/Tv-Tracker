package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.series.Serie;

public class CastFragment extends Fragment {

    private RecyclerView rvCasting;
    private ActorBasicAdapter adapterActor;
    private Context mContext;
    private Serie mSerie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        rvCasting = view.findViewById(R.id.gridCasting);
        rvCasting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        LiveData<Serie> s = model.getSerie();
        s.observe(getViewLifecycleOwner(), serie -> {
            if (!serie.credits.cast.isEmpty()) {
                adapterActor = new ActorBasicAdapter(mContext, serie);
                rvCasting.setAdapter(adapterActor);
            } else {
                Snackbar.make(view, "Sin datos de reparto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
