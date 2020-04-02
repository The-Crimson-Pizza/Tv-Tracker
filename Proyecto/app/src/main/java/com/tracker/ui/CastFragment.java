package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.models.SerieViewModel;
import com.tracker.models.series.Serie;

public class CastFragment extends Fragment {

    private SerieViewModel model;
    private RecyclerView rvCasting;
    private ActorBasicAdapter adapterActor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        rvCasting = view.findViewById(R.id.gridCasting);
        rvCasting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SerieViewModel.class);
        LiveData<Serie> s = model.getCurrentSerie();
        s.observe(getViewLifecycleOwner(), new Observer<Serie>() {
            @Override
            public void onChanged(Serie serie) {
                adapterActor = new ActorBasicAdapter(getActivity(), serie);
                rvCasting.setAdapter(adapterActor);
                adapterActor.notifyDataSetChanged();
            }
        });
    }
}
