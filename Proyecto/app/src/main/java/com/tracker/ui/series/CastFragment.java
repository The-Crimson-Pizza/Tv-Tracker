package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.serie.Credits;
import com.tracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

public class CastFragment extends Fragment {

    private Context mContext;
    private SerieResponse.Serie mSerie;
    private List<Credits.Cast> mCast = new ArrayList<>();

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
        ViewSwitcher switcherCast = view.findViewById(R.id.switcher_cast);

        ActorBasicAdapter adapterActor = new ActorBasicAdapter(mContext, mCast);
        RecyclerView rvCasting = view.findViewById(R.id.gridCasting);
        setRecycler(adapterActor, rvCasting);

        LiveData<SerieResponse.Serie> s = model.getSerie();
        s.observe(getViewLifecycleOwner(), serie -> {
            mCast.clear();
            if (!serie.credits.cast.isEmpty()) {
                mSerie = serie;
                mCast.addAll(mSerie.credits.cast);
                adapterActor.notifyDataSetChanged();
                if (R.id.gridCasting == switcherCast.getNextView().getId())
                    switcherCast.showNext();
            } else {
                adapterActor.notifyDataSetChanged();
                if (R.id.no_data_cast == switcherCast.getNextView().getId())
                    switcherCast.showNext();
//                Snackbar.make(view, R.string.no_cast, LENGTH_SHORT).show();
            }
        });
    }

    private void setRecycler(ActorBasicAdapter adapterActor, RecyclerView rvCasting) {
        rvCasting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCasting.setHasFixedSize(true);
        rvCasting.setItemViewCacheSize(20);
        rvCasting.setSaveEnabled(true);
        rvCasting.setAdapter(adapterActor);
    }

}
