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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.HomeAdapter;
import com.tracker.adapters.NetworkGenreAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.BasicResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.tracker.util.Constants.ID_GENRE;


public class GenreFragment extends Fragment {

    private int idGenre;
    private Context mContext;
    private NetworkGenreAdapter genreAdapter;
    List<BasicResponse.SerieBasic> mSeriesByGenre = new ArrayList<>();

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGenre = getArguments().getInt(ID_GENRE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewSwitcher switcher = view.findViewById(R.id.switcher_genres);
        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        genreAdapter = new NetworkGenreAdapter(mContext, mSeriesByGenre, true);

        setRecycler(view);
        getSeriesByGenre();

    }

    private void getSeriesByGenre() {
        RepositoryAPI.getInstance().getByGenre(idGenre)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mSeriesByGenre.clear();
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull BasicResponse basicResponse) {
                        mSeriesByGenre.clear();
                        mSeriesByGenre.addAll(basicResponse.basicSeries);
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mSeriesByGenre.clear();
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }

    private void setRecycler(View view) {
        RecyclerView rvNetworks = view.findViewById(R.id.rv_genres);
        rvNetworks.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvNetworks.setHasFixedSize(true);
        rvNetworks.setItemViewCacheSize(20);
        rvNetworks.setSaveEnabled(true);
        rvNetworks.setAdapter(genreAdapter);
    }
}
