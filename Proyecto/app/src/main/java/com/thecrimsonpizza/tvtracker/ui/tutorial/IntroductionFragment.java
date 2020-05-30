package com.thecrimsonpizza.tvtracker.ui.tutorial;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.appintro.SlidePolicy;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.TutorialAdapter;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.BasicResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class IntroductionFragment extends Fragment implements SlidePolicy {

    private final List<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private Context mContext;
    private boolean isOn;

    private TutorialAdapter adapterPopular;
    private CompositeDisposable compositeDisposable;

    Button b;
    boolean pulsado = false;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    public static IntroductionFragment newInstance() {
        IntroductionFragment fragment = new IntroductionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.tutorial_slide5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compositeDisposable = new CompositeDisposable();

        adapterPopular = new TutorialAdapter(getActivity(), mPopulares);
        RecyclerView rvPopulares = view.findViewById(R.id.rv_intro);
        initRecycler(rvPopulares, adapterPopular);

        initHome(view);
    }

    private void initRecycler(RecyclerView rv, TutorialAdapter ha) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv.setAdapter(ha);
    }

    private void initHome(View view) {
        if (getConnectivityStatus()) {
            isOn = true;
            getTrending(adapterPopular);
        } else {
            isOn = false;
            Snackbar.make(view, getString(R.string.no_conn), BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction(R.string.activate_net, v1 -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }
    }

    private void getTrending(TutorialAdapter adapterPopular) {
        Disposable disposable = TmdbRepository.getInstance().getTrendingSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(series -> refreshData(mPopulares, adapterPopular, series.basicSeries));
        compositeDisposable.add(disposable);
    }

    private void refreshData(List<BasicResponse.SerieBasic> lista, TutorialAdapter adapter, List<BasicResponse.SerieBasic> response) {
        lista.clear();
        lista.addAll(response);
        adapter.notifyDataSetChanged();
    }

    private boolean getConnectivityStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = null;
        if (connectivityManager != null) {
            capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        }
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }

    @Override
    public boolean isPolicyRespected() {
//        return pulsado;
        return true;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Log.e("q", "NO PULSADO");
    }
}
