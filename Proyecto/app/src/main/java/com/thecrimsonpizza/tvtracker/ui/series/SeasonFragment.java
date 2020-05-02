package com.thecrimsonpizza.tvtracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.SeasonAdapter;
import com.thecrimsonpizza.tvtracker.data.FirebaseDb;
import com.thecrimsonpizza.tvtracker.data.RxBus;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SeasonFragment extends Fragment {

    private Context mContext;
    private SeasonAdapter mSeasonAdapter;
    private RecyclerView rvSeasons;
    private SerieResponse.Serie mSerie;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private CompositeDisposable compositeDisposable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_seasons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecycler(view);
        getSerie();
        getFollowing();
    }

    private void getSerie() {
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = RxBus.getInstance().listen().subscribe(this::setAdapters);
        compositeDisposable.add(disposable);
    }

    private void getFollowing() {
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<SerieResponse.Serie>> genericTypeIndicator = new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                };
                mFavs.clear();
                List<SerieResponse.Serie> favTemp = dataSnapshot.getValue(genericTypeIndicator);
                if (favTemp != null) {
                    mFavs.addAll(favTemp);
                    if (mSerie != null) {
                        mSerie.checkFav(mFavs);
                        rvSeasons.setAdapter(new SeasonAdapter(mContext, mSerie, mFavs));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nada de momento
            }
        });
    }


    private void setAdapters(SerieResponse.Serie serie) {
        mSerie = serie;
        mSeasonAdapter = new SeasonAdapter(mContext, mSerie, mFavs);
        rvSeasons.setAdapter(mSeasonAdapter);
    }

    private void setRecycler(@NonNull View view) {
        rvSeasons = view.findViewById(R.id.gridSeasons);
        rvSeasons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvSeasons.setHasFixedSize(true);
        rvSeasons.setItemViewCacheSize(20);
        rvSeasons.setSaveEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
