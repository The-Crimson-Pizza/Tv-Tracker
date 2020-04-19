package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.FillSerie;
import com.tracker.adapters.TabLayoutAdapter;
import com.tracker.data.FirebaseDb;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.RxBus;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.ID_SERIE;

public class SerieFragment extends Fragment {

    private int idSerie;
    private Context mContext;
    private SeriesViewModel seriesViewModel;
    private SerieResponse.Serie mSerie;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idSerie = getArguments().getInt(ID_SERIE);
        }
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_serie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seriesViewModel = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        hideKeyboard();
        setToolbar(view);
        setViewPager(view);

//        ProgressBar bar = view.findViewById(R.id.progreso);
//        bar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(viewFab -> {
            if (!mSerie.fav) {
                addFav();
                Snackbar.make(viewFab, R.string.add_fav, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(viewFab, R.string.already_fav, Snackbar.LENGTH_LONG)
                        .setAction(R.string.delete_fav, viewSnack -> deleteFav()).show();
            }
        });

        FirebaseDb.getInstance().getSeriesFav().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<SerieResponse.Serie>> genericTypeIndicator = new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                };
                mFavs.clear();
                if (dataSnapshot.getValue(genericTypeIndicator) != null) {
                    mFavs.addAll(dataSnapshot.getValue(genericTypeIndicator));
                }
                if (mSerie == null) {
                    getSerie(view);
                } else {
                    setProgress(mSerie, view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                nada de momento
            }
        });
    }

    private void getSerie(View view) {
        RepositoryAPI.getInstance().getSerie(idSerie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(serie -> {
                    mSerie = serie;
                    getSeasons(mSerie, view);
                });
    }

    private void getSeasons(SerieResponse.Serie s, View view) {
        RepositoryAPI.getInstance().getSeasons(s.id, 1, s.numberOfSeasons)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lista -> {
                    s.seasons = lista;
                    Season.sortSeason(s.seasons);
                    setProgress(s, view);
                });
    }

    private void setProgress(SerieResponse.Serie s, View view) {
        if (mFavs != null) {
            s.checkFav(mFavs);
        }
        RxBus.getInstance().publish(s);
        seriesViewModel.setSerie(s);
        new FillSerie(view, s, mContext).fillCollapseBar();
    }

    private void addFav() {
        mSerie.fav = true;
        mFavs.add(mSerie);
        FirebaseDb.getInstance().setSeriesFav(mFavs);
        RxBus.getInstance().publish(mSerie);
    }

    private void deleteFav() {
        int pos = mSerie.getPosition(mFavs);
        if (pos != -1) {
            mFavs.remove(pos);
            FirebaseDb.getInstance().setSeriesFav(mFavs);
            mSerie.fav = false;
            RxBus.getInstance().publish(mSerie);
            Toast.makeText(getActivity(), R.string.borrado_correcto, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void setViewPager(@NonNull View view) {
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new TabLayoutAdapter(this, false));

        String[] tabs = {getString(R.string.sinopsis), getString(R.string.reparto), getString(R.string.temporadas)};
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        new TabLayoutMediator(tabLayout, viewPager, false,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();
    }

    private void setToolbar(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }
}
