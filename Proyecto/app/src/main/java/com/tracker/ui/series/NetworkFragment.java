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
import com.tracker.adapters.NetworkGenreAdapter;
import com.tracker.models.BasicResponse;
import com.tracker.repositories.SeriesViewModel;
import com.tracker.repositories.TmdbRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.tracker.util.Constants.ID_NETWORK;


public class NetworkFragment extends Fragment {

    private int idNetwork;
    private Context mContext;
    private NetworkGenreAdapter networkAdapter;
    List<BasicResponse.SerieBasic> seriesByNetwork = new ArrayList<>();


    public NetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idNetwork = getArguments().getInt(ID_NETWORK);
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

        ViewSwitcher switcher = view.findViewById(R.id.switcher_networks);
        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        networkAdapter = new NetworkGenreAdapter(mContext, seriesByNetwork, false);


        setRecycler(view);
        getSeriesByNetwork();

    }

    private void getSeriesByNetwork() {
        TmdbRepository.getInstance().getByNetwork(idNetwork)
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
