package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.FillSerie;
import com.tracker.adapters.TabLayoutAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.RxBus;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.SerieFav;
import com.tracker.models.serie.SerieResponse;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.tracker.util.Constants.ID_SERIE;

public class SerieFragment extends Fragment {

    private int idSerie;
    private Context mContext;
    private boolean isFav = false;
    private SeriesViewModel seriesViewModel;
    private DatabaseReference databaseRef;
    private SerieResponse.Serie mSerie;
    private List<SerieFav> mFavs;

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
            if (!isFav) {
                addFav();
                Snackbar.make(viewFab, R.string.add_fav, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(viewFab, R.string.already_fav, Snackbar.LENGTH_LONG)
                        .setAction(R.string.delete_fav, viewSnack -> deleteFav()).show();
            }
        });

        databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<SerieFav>> genericTypeIndicator = new GenericTypeIndicator<List<SerieFav>>() {
                };
                mFavs = dataSnapshot.getValue(genericTypeIndicator);
                getSerie(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSerie(View view) {
        RepositoryAPI.getInstance().getSerie(idSerie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(serie -> {
                    mSerie = serie;
                    isFav = SerieFav.checkFav(mSerie, mFavs);
                    if (isFav) {
                        mSerie.isFav = true;
                    }
                    RxBus.getInstance().publish(mSerie);
                    seriesViewModel.setSerie(mSerie);
                    new FillSerie(view, mSerie, mContext).fillCollapseBar();
                });
    }

    private void addFav() {
        SerieFav s = mSerie.convertSerieToFav();
        RepositoryAPI.getInstance().getSeasons(s.id, s.seasons.get(0).seasonNumber, s.numberOfSeasons)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lista -> {
                    s.seasons = lista;
                    mFavs.add(s);
                    databaseRef.setValue(mFavs);
                });

        mSerie.isFav = true;
        RxBus.getInstance().publish(mSerie);
        isFav = true;
    }

    private void deleteFav() {
        int pos = SerieFav.getPosition(mSerie, mFavs);
        if (pos != -1) {
            SerieFav serieDeleted = mFavs.get(pos);
            mFavs.remove(serieDeleted);
            databaseRef.setValue(mFavs);
            Toast.makeText(getActivity(), R.string.borrado_correcto, Toast.LENGTH_SHORT).show();
            mSerie.isFav = false;
            RxBus.getInstance().publish(mSerie);
            isFav = false;
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
