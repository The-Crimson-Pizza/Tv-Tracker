package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.tracker.models.series.SerieResponse;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class CastFragment extends Fragment {

    private RecyclerView rvCasting;
    private ActorBasicAdapter adapterActor;
    private Context mContext;
    private SerieResponse.Serie mSerie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvCasting = view.findViewById(R.id.gridCasting);
        rvCasting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCasting.setHasFixedSize(true);
        rvCasting.setItemViewCacheSize(20);
        rvCasting.setSaveEnabled(true);

        LiveData<SerieResponse.Serie> s = model.getSerie();
        s.observe(getViewLifecycleOwner(), serie -> {
            if (!serie.credits.cast.isEmpty()) {
                mSerie = serie;
                adapterActor = new ActorBasicAdapter(mContext, mSerie);
                rvCasting.setAdapter(adapterActor);
            } else {
                adapterActor = new ActorBasicAdapter(mContext, null);
                rvCasting.setAdapter(adapterActor);
                Snackbar.make(view, R.string.no_cast, LENGTH_SHORT).show();
            }
        });
    }

}
