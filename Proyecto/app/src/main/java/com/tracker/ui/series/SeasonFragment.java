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
import com.tracker.adapters.SeasonAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Season;
import com.tracker.models.series.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE;

public class SeasonFragment extends Fragment {

    private Context mContext;
    private List<Season> mSeasonList;
    private SeasonAdapter adapter;
    private RecyclerView rvSeasons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seasons, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvSeasons = view.findViewById(R.id.gridSeasons);
        rvSeasons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvSeasons.setHasFixedSize(true);
        rvSeasons.setItemViewCacheSize(20);
        rvSeasons.setSaveEnabled(true);

        LiveData<SerieResponse.Serie> s = model.getSerie();
        s.observe(getViewLifecycleOwner(), serie -> {
            if (serie.numberOfSeasons > 0) {
                getSeasons(serie);
            } else {
                Snackbar.make(view, R.string.no_seasons, LENGTH_INDEFINITE).show();
            }
        });
    }

    private void getSeasons(SerieResponse.Serie serie) {
        RepositoryAPI.getInstance().getSeason(serie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seasons -> {
                    mSeasonList = seasons;
                    adapter = new SeasonAdapter(mContext, mSeasonList);
                    rvSeasons.setAdapter(adapter);
                });
    }
}
