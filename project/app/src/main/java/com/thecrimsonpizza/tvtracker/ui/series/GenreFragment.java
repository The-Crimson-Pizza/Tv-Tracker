package com.thecrimsonpizza.tvtracker.ui.series;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.NetworkGenreAdapter;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.BasicResponse;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;
import com.thecrimsonpizza.tvtracker.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;
import static com.thecrimsonpizza.tvtracker.util.Constants.ID_GENRE;


public class GenreFragment extends Fragment {

    private Context mContext;
    private SerieResponse.Serie.Genre mGenre;
    private NetworkGenreAdapter genreAdapter;
    private List<BasicResponse.SerieBasic> mSeriesByGenre = new ArrayList<>();

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getParcelable(ID_GENRE);
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

        genreAdapter = new NetworkGenreAdapter(mContext, mSeriesByGenre);

        ImageView ivGenreIcon = view.findViewById(R.id.genre_icon);
        TextView tvGenre = view.findViewById(R.id.genre_name);

        setRecycler(view);


        if (Util.isNetworkAvailable(mContext)) {
            if (mGenre != null) getSeriesByGenre();
        } else {
            Snackbar.make(view, mContext.getString(R.string.no_conn), LENGTH_LONG)
                    .setAction(R.string.activate_net, v1 -> mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }

        setGenreIcon(ivGenreIcon, tvGenre);

    }

    private void setGenreIcon(ImageView genreIcon, TextView tvGenre) {
        if (mGenre != null) {
            String genreName = mGenre.name;
            tvGenre.setText(genreName);
            if (mGenre.id == 10759) {
                genreIcon.setImageResource(R.drawable.genre_adventure);
            } else if (mGenre.id == 16) {
                genreIcon.setImageResource(R.drawable.genre_animation);
            } else if (mGenre.id == 35) {
                genreIcon.setImageResource(R.drawable.genre_comedy);
            } else if (mGenre.id == 99) {
                genreIcon.setImageResource(R.drawable.genre_camera);
            } else if (mGenre.id == 18) {
                genreIcon.setImageResource(R.drawable.genre_drama);
            } else if (mGenre.id == 10751) {
                genreIcon.setImageResource(R.drawable.genre_family);
            } else if (mGenre.id == 10762) {
                genreIcon.setImageResource(R.drawable.genre_kids);
            } else if (mGenre.id == 9648) {
                genreIcon.setImageResource(R.drawable.genre_mistery);
            } else if (mGenre.id == 80) {
                genreIcon.setImageResource(R.drawable.genre_crime);
            } else if (mGenre.id == 10763) {
                genreIcon.setImageResource(R.drawable.genre_news);
            } else if (mGenre.id == 10764) {
                genreIcon.setImageResource(R.drawable.genre_reality);
            } else if (mGenre.id == 10765) {
                genreIcon.setImageResource(R.drawable.genre_sci_fi);
            } else if (mGenre.id == 10766) {
                genreIcon.setImageResource(R.drawable.genre_soap);
            } else if (mGenre.id == 10767) {
                genreIcon.setImageResource(R.drawable.genre_talk);
            } else if (mGenre.id == 10768) {
                genreIcon.setImageResource(R.drawable.genre_war);
            } else if (mGenre.id == 37) {
                genreIcon.setImageResource(R.drawable.genre_western);
            } else {
                genreIcon.setImageResource(R.drawable.genre_documental);
            }
        }
    }

    private void getSeriesByGenre() {
        TmdbRepository.getInstance().getByGenre(mGenre.id)
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
