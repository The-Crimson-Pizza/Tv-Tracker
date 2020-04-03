package com.tracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.R;
import com.tracker.adapters.RellenarSerie;
import com.tracker.adapters.SeriesViewModel;
import com.tracker.models.series.Serie;

public class SinopsisFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Serie mSerie;
//    private SerieViewModel model;

    public SinopsisFragment() {
        // Required empty public constructor
    }

    public static SinopsisFragment newInstance() {
        SinopsisFragment fragment = new SinopsisFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sinopsis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        LiveData<Serie> s = model.getCurrentSerie();
        s.observe(getViewLifecycleOwner(), new Observer<Serie>() {
            @Override
            public void onChanged(Serie serie) {
                new RellenarSerie(view, serie, getActivity()).fillSerieSinopsis();
            }
        });
    }


}
