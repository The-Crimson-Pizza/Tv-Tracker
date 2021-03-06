package com.thecrimsonpizza.tvtracker.ui.series;

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

import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.ActorBasicAdapter;
import com.thecrimsonpizza.tvtracker.data.RxBus;
import com.thecrimsonpizza.tvtracker.models.serie.Credits;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class CastFragment extends Fragment {

    private Context mContext;
    private SerieResponse.Serie mSerie;
    private final List<Credits.Cast> mCast = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewSwitcher switcherCast = view.findViewById(R.id.switcher_cast);

        ActorBasicAdapter adapterActor = new ActorBasicAdapter(mContext, mCast);
        RecyclerView rvCasting = view.findViewById(R.id.gridCasting);
        setRecycler(adapterActor, rvCasting);

        RxBus.getInstance().listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerieResponse.Serie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // Nada de momento
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull SerieResponse.Serie serie) {
                        mCast.clear();
                        if (serie.credits.cast != null) {
                            mSerie = serie;
                            mCast.addAll(mSerie.credits.cast);
                            adapterActor.notifyDataSetChanged();
                            if (R.id.gridCasting == switcherCast.getNextView().getId())
                                switcherCast.showNext();
                        }
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

    private void setRecycler(ActorBasicAdapter adapterActor, RecyclerView rvCasting) {
        rvCasting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCasting.setHasFixedSize(true);
        rvCasting.setItemViewCacheSize(20);
        rvCasting.setSaveEnabled(true);
        rvCasting.setAdapter(adapterActor);
    }
}
