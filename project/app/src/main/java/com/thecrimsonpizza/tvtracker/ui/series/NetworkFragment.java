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
import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_NETWORK;
import static com.thecrimsonpizza.tvtracker.util.Constants.ID_NETWORK;

public class NetworkFragment extends Fragment {

    private Context mContext;
    private boolean isOn;
    private NetworkGenreAdapter networkAdapter;
    private SerieResponse.Serie.Network mNetwork;
    private List<BasicResponse.SerieBasic> seriesByNetwork = new ArrayList<>();


    public NetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNetwork = getArguments().getParcelable(ID_NETWORK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivNetwork = view.findViewById(R.id.network_icon);
        TextView tvNetwork = view.findViewById(R.id.network_name);
        tvNetwork.setText(mNetwork.name);
        Util.getImage(BASE_URL_IMAGES_NETWORK + mNetwork.logoPath, ivNetwork, mContext);

        networkAdapter = new NetworkGenreAdapter(mContext, seriesByNetwork);

        setRecycler(view);
        initNetwork(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOn) initNetwork(requireView());
    }

    private void initNetwork(@NonNull View view) {
        if (Util.isNetworkAvailable(mContext)) {
            isOn = true;
            getSeriesByNetwork();
        } else {
            isOn = false;
            Snackbar.make(view, mContext.getString(R.string.no_conn), LENGTH_LONG)
                    .setAction(R.string.activate_net, v1 -> mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }
    }

    private void getSeriesByNetwork() {
        TmdbRepository.getInstance().getByNetwork(mNetwork.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        seriesByNetwork.clear();
                        networkAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull BasicResponse basicResponse) {
                        seriesByNetwork.clear();
                        seriesByNetwork.addAll(basicResponse.basicSeries);
                        networkAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        seriesByNetwork.clear();
                        networkAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }

    private void setRecycler(View view) {
        RecyclerView rvNetworks = view.findViewById(R.id.rvNetworks);
        rvNetworks.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvNetworks.setHasFixedSize(true);
        rvNetworks.setItemViewCacheSize(20);
        rvNetworks.setSaveEnabled(true);
        rvNetworks.setAdapter(networkAdapter);
    }
}
