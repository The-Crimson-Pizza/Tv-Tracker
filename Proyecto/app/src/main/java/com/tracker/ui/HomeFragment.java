package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

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
import com.tracker.R;
import com.tracker.adapters.HomeAdapter;
import com.tracker.models.BasicResponse;
import com.tracker.models.serie.SerieResponse;
import com.tracker.repositories.FirebaseDb;
import com.tracker.repositories.TmdbRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class HomeFragment extends Fragment {

    private List<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private List<BasicResponse.SerieBasic> mNuevas = new ArrayList<>();
    private List<BasicResponse.SerieBasic> mFavs = new ArrayList<>();
    private Context mContext;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeAdapter adapterPopular = new HomeAdapter(getActivity(), mPopulares);
        HomeAdapter adapterNueva = new HomeAdapter(getActivity(), mNuevas);
        HomeAdapter adapterFav = new HomeAdapter(getActivity(), mFavs);

        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);
        RecyclerView rvFavs = view.findViewById(R.id.gridFavs);
        ViewSwitcher switcherFavs = view.findViewById(R.id.switcher_favs);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        initRecycler(rvNuevas, adapterNueva);
        initRecycler(rvPopulares, adapterPopular);
        initRecycler(rvFavs, adapterFav);

        TmdbRepository.getInstance().getTrendingSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> refreshData(mPopulares, adapterPopular, series.basicSeries));

        TmdbRepository.getInstance().getNewSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> refreshData(mNuevas, adapterNueva, series.basicSeries));

        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                GenericTypeIndicator<List<SerieResponse.Serie>> genericTypeIndicator = new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                };
                List<SerieResponse.Serie> favTemp = dataSnapshot.getValue(genericTypeIndicator);
                if (favTemp != null) {
                    for (SerieResponse.Serie fav : favTemp) {
                        mFavs.add(fav.toBasic());
                    }
                    adapterFav.notifyDataSetChanged();
                    if (R.id.gridFavs == switcherFavs.getNextView().getId())
                        switcherFavs.showNext();
                } else {
                    if (R.id.no_data_favs == switcherFavs.getNextView().getId())
                        switcherFavs.showNext();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void refreshData(List<BasicResponse.SerieBasic> lista, HomeAdapter adapter, List<BasicResponse.SerieBasic> response) {
        lista.clear();
        lista.addAll(response);
        adapter.notifyDataSetChanged();
    }

    private void initRecycler(RecyclerView rv, HomeAdapter adapter) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter);
    }
}