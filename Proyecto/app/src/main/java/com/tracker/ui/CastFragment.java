package com.tracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.R;
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.adapters.SeriesBasicAdapter;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.SerieViewModel;
import com.tracker.models.series.Serie;

import static com.tracker.util.Constants.ID_SERIE;
import static com.tracker.util.Constants.TAG;

public class CastFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SerieViewModel model;
    private Serie mSerie;
    private RecyclerView rvCasting;
    private ActorBasicAdapter adapterActor;

    public CastFragment() {
        // Required empty public constructor
    }

    public static CastFragment newInstance(String param1, String param2) {
        CastFragment fragment = new CastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        rvCasting = view.findViewById(R.id.gridCasting);
        rvCasting.setLayoutManager(new GridLayoutManager(getActivity(), 3));
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
                mSerie = serie;
                adapterActor = new ActorBasicAdapter(getActivity(), serie);
                rvCasting.setAdapter(adapterActor);
                adapterActor.notifyDataSetChanged();
            }
        });
    }
}
