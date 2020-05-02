package com.thecrimsonpizza.tvtracker.ui.series;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.FillSerie;
import com.thecrimsonpizza.tvtracker.adapters.TabLayoutAdapter;
import com.thecrimsonpizza.tvtracker.data.FirebaseDb;
import com.thecrimsonpizza.tvtracker.data.RxBus;
import com.thecrimsonpizza.tvtracker.data.SeriesViewModel;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.seasons.Season;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;
import com.thecrimsonpizza.tvtracker.ui.WebViewActivity;
import com.thecrimsonpizza.tvtracker.util.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_WEB_TV;
import static com.thecrimsonpizza.tvtracker.util.Constants.ID_SERIE;
import static com.thecrimsonpizza.tvtracker.util.Constants.TEXT_PLAIN;
import static com.thecrimsonpizza.tvtracker.util.Constants.URL_WEBVIEW;

public class SerieFragment extends Fragment {

    private int idSerie;
    private Context mContext;
    private SeriesViewModel seriesViewModel;
    private SerieResponse.Serie mSerie;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    private MenuItem itemWeb;

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

        seriesViewModel = new ViewModelProvider(requireActivity()).get(SeriesViewModel.class);

        setToolbar(view);
        hideKeyboard();
        setViewPager(view);
        setFloatingButton(view);
        getFollowingSeries(view);
    }

    private void getFollowingSeries(View view) {
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<SerieResponse.Serie>> genericTypeIndicator = new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                };
                mFavs.clear();
                if (dataSnapshot.getValue(genericTypeIndicator) != null) {
                    mFavs.addAll(dataSnapshot.getValue(genericTypeIndicator));
                }
                setSerie(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                setSerie(view);

            }
        });
    }

    private void setSerie(View view) {
        if (mSerie == null) {
            if (Util.isNetworkAvailable(mContext)) {
                getSerie(view);
            } else {
                Snackbar.make(view, R.string.no_conn, BaseTransientBottomBar.LENGTH_INDEFINITE).show();
            }

        } else {
            setProgress(mSerie, view);
            if (mSerie.homepage != null && !mSerie.homepage.isEmpty()) {
                itemWeb.setVisible(true);
            }
        }
    }

    private void getSerie(View view) {
        TmdbRepository.getInstance().getSerie(idSerie)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("TAG", throwable.getLocalizedMessage()))
                .subscribe(serie -> {
                    mSerie = serie;
                    if (mSerie.homepage != null && !mSerie.homepage.isEmpty()) {
                        itemWeb.setVisible(true);
                    }
                    getSeasons(mSerie, view);
                });
    }

    private void getSeasons(SerieResponse.Serie s, View view) {
        TmdbRepository.getInstance().getSeasons(s.id, 1, s.numberOfSeasons)
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
        mSerie.added = true;
        mSerie.addedDate = new Date();
        mFavs.add(mSerie);
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(mFavs);
        RxBus.getInstance().publish(mSerie);
    }

    private void deleteFav() {
        int pos = mSerie.getPosition(mFavs);
        if (pos != -1) {
            mFavs.remove(pos);
            FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(mFavs);
            mSerie.added = false;
            mSerie.addedDate = null;
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
        itemWeb = toolbar.getMenu().findItem(R.id.action_web);
        itemWeb.setVisible(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                startActivity(Intent.createChooser(setIntent(), null));
                return true;
            } else if (item.getItemId() == R.id.action_web) {
                startActivity(new Intent(mContext, WebViewActivity.class).putExtra(URL_WEBVIEW, mSerie.homepage));
                return true;
            }
            return false;
        });
    }

    @NotNull
    private Intent setIntent() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, mSerie.name);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sharing_url));
        sendIntent.putExtra(Intent.EXTRA_TEXT, BASE_URL_WEB_TV + mSerie.id);
        sendIntent.setType(TEXT_PLAIN);
        return sendIntent;
    }

    private void setFloatingButton(@NonNull View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(viewFab -> {
            if (!mSerie.added) {
                addFav();
                Snackbar.make(viewFab, R.string.add_fav, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(viewFab, R.string.already_fav, Snackbar.LENGTH_LONG)
                        .setAction(R.string.delete_fav, viewSnack -> deleteFav()).show();
            }
        });
    }
}
