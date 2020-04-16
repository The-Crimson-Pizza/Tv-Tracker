package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tracker.R;
import com.tracker.adapters.FillSerie;
import com.tracker.adapters.TabLayoutAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.RxBus;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.SerieFav;
import com.tracker.models.serie.SerieResponse;
import com.tracker.util.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.ID_SERIE;
import static com.tracker.util.Constants.URL_FAV;

public class SerieFragment extends Fragment {

    private int idSerie;
    private SerieResponse.Serie mSerie;
    private List<SerieFav> mFavs = new ArrayList<>();
    private SeriesViewModel model;
    private Context mContext;
    private boolean isFav = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_serie, container, false);
        mContext = getActivity();
//        getActivity().getApplicationContext()
        if (getArguments() != null) {
            idSerie = getArguments().getInt(ID_SERIE);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        hideKeyboard();
        setToolbar(view);
        setViewPager(view);

//        ProgressBar bar = view.findViewById(R.id.progreso);
//        bar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            SerieFav.readFav(mContext.getFilesDir() + URL_FAV, model);
            if (!isFav) {
                SerieFav.writeFav(mFavs, mSerie, mContext.getFilesDir() + URL_FAV, model);
                RxBus.getInstance().publish(mSerie);
                isFav = true;
                Snackbar.make(v, R.string.add_fav, Snackbar.LENGTH_LONG)
                        .setAction("Undo", null).show();

            } else {
                Snackbar.make(v, R.string.already_fav, Snackbar.LENGTH_LONG)
                        .setAction(R.string.delete_fav, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try (Writer writer = new FileWriter(mContext.getFilesDir() + URL_FAV)) {
                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                    int pos = SerieFav.getPosition(mSerie, mFavs);
                                    if (pos != -1) {
                                        SerieFav p = mFavs.get(pos);
                                        mFavs.remove(p);
                                        gson.toJson(mFavs, writer);
                                        Toast.makeText(getActivity(), R.string.borrado_correcto, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                SerieFav.readFav(mContext.getFilesDir() + URL_FAV, model);
                                mSerie.isFav = false;
                                RxBus.getInstance().publish(mSerie);
                                isFav = false;
                            }
                        }).show();
            }

        });

        LiveData<List<SerieFav>> seriesFavs = model.getFavs();
        seriesFavs.observe(getViewLifecycleOwner(), series -> {
            if (series != null) {
                mFavs.clear();
                mFavs.addAll(series);
            }
        });

        getSerie(view);
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


    private void getSerie(View view) {

        RepositoryAPI.getInstance().getSerie(idSerie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(serie -> {
                    mSerie = serie;
                    isFav = SerieFav.checkFav(mSerie, mFavs);
                    if (isFav) {
                        mSerie.isFav = true;
                    }
                    SerieFav.readFav(mContext.getFilesDir() + URL_FAV, model);

                    model.setSerie(mSerie);
                    RxBus.getInstance().publish(mSerie);
                    new FillSerie(view, mSerie, mContext).fillCollapseBar();
                });
    }
}
