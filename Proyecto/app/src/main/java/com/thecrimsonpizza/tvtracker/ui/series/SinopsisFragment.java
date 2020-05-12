package com.thecrimsonpizza.tvtracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.FillSerie;
import com.thecrimsonpizza.tvtracker.data.RxBus;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SinopsisFragment extends Fragment {

    private SerieResponse.Serie mSerie;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sinopsis, container, false);
        YouTubePlayerView youTubePlayerView = root.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        mContext = requireActivity().getApplicationContext();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = view.findViewById(R.id.progreso);

        RxBus.getInstance().listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerieResponse.Serie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // Nada de momento
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull SerieResponse.Serie serie) {
                        mSerie = serie;
                        new FillSerie(view, mSerie, mContext).fillOverview();
                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        // Nada de momento
                    }

                    @Override
                    public void onComplete() {
                        // Nada de momento
                    }
                });
    }
}
