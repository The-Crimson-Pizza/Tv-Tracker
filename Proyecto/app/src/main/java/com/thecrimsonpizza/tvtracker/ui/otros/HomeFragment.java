package com.thecrimsonpizza.tvtracker.ui.otros;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.HomeAdapter;
import com.thecrimsonpizza.tvtracker.data.FirebaseDb;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.BasicResponse;
import com.thecrimsonpizza.tvtracker.models.seasons.Season;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment {

    private final List<BasicResponse.SerieBasic> mPopulares = new ArrayList<>();
    private final List<BasicResponse.SerieBasic> mNuevas = new ArrayList<>();
    private final List<BasicResponse.SerieBasic> mFavs = new ArrayList<>();
    List<SerieResponse.Serie> favTemp = new ArrayList<>();

    private Context mContext;
    private boolean isOn;

    private HomeAdapter adapterPopular;
    private HomeAdapter adapterNueva;
    private HomeAdapter adapterFav;
    private ViewSwitcher switcherFavs;
    private CompositeDisposable compositeDisposable;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        compositeDisposable = new CompositeDisposable();

        adapterPopular = new HomeAdapter(getActivity(), mPopulares);
        adapterNueva = new HomeAdapter(getActivity(), mNuevas);
        adapterFav = new HomeAdapter(getActivity(), mFavs);

        switcherFavs = view.findViewById(R.id.switcher_favs);
        RecyclerView rvPopulares = view.findViewById(R.id.gridPopulares);
        RecyclerView rvNuevas = view.findViewById(R.id.gridNuevas);
        RecyclerView rvFavs = view.findViewById(R.id.gridFavs);

        initRecycler(rvNuevas, adapterNueva);
        initRecycler(rvPopulares, adapterPopular);
        initRecycler(rvFavs, adapterFav);

        initHome(view);
    }

    private void initHome(@NonNull View view) {
        if (getConnectivityStatus()) {
            isOn = true;
            getTrending(adapterPopular);
            getNew(adapterNueva);

            SharedPreferences prefs = mContext.getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            List<Integer> temp = getPrefIntArray(prefs, new int[]{});
            if (temp != null && !temp.isEmpty()) saveFollowing(prefs, temp);
            else getFavorites(adapterFav, switcherFavs);

        } else {
            isOn = false;
            Snackbar.make(view, getString(R.string.no_conn), BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction(R.string.activate_net, v1 -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))).show();
        }
    }

    public List<Integer> getPrefIntArray(SharedPreferences sp, int[] defaultValue) {
        String s = sp.getString("TEMP_DATA", "");

        try {
            JSONObject json = new JSONObject(new JSONTokener(s));
            JSONArray jsonArr = json.getJSONArray("TEMP_DATA");

            int[] result = new int[jsonArr.length()];

            for (int i = 0; i < jsonArr.length(); i++)
                result[i] = jsonArr.getInt(i);

            return Arrays.stream(result).boxed().collect(Collectors.toList());

        } catch (JSONException excp) {
            return Arrays.stream(defaultValue).boxed().collect(Collectors.toList());
        }
    }

    private void saveFollowing(SharedPreferences sp, List<Integer> temp) {
        if (temp != null) {
            for (int id : temp) {
                getSerie(id);
            }
            SharedPreferences.Editor prefEditor = sp.edit();
            prefEditor.putString("TEMP_DATA", "");
            prefEditor.commit();
        }
    }

    private void getSerie(int id) {
        TmdbRepository.getInstance().getSerie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getSeasons);
    }

    private void getSeasons(SerieResponse.Serie s) {
        TmdbRepository.getInstance().getSeasons(s.id, 1, s.numberOfSeasons)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lista -> {
                    s.seasons = lista;
                    Season.sortSeason(s.seasons);
                    favTemp.add(s);
                    getFavorites(adapterFav, switcherFavs);
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOn) {
            initHome(requireView());
        }
    }

    private void getFavorites(HomeAdapter adapterFav, ViewSwitcher switcherFavs) {
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                List<SerieResponse.Serie> temp = dataSnapshot.getValue(new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                });
                if (temp != null) {
                    if (!favTemp.isEmpty()) {
                        temp.addAll(favTemp);
                        favTemp = new ArrayList<>();
                        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(temp);
                    }
                    for (SerieResponse.Serie fav : temp) {
                        if (!fav.toBasic().isFav(mFavs))
                            mFavs.add(fav.toBasic());
                    }

                    adapterFav.notifyDataSetChanged();
                    if (R.id.gridFavs == switcherFavs.getNextView().getId())
                        switcherFavs.showNext();
                } else {
                    if (!favTemp.isEmpty())
                        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).setSeriesFav(favTemp);
                    if (R.id.no_data_favs == switcherFavs.getNextView().getId())
                        switcherFavs.showNext();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Empty
            }
        });
    }

    private void getNew(HomeAdapter adapterNueva) {
        Disposable disposable = TmdbRepository.getInstance().getNewSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(series -> refreshData(mNuevas, adapterNueva, series.basicSeries));
        compositeDisposable.add(disposable);
    }

    private void getTrending(HomeAdapter adapterPopular) {
        Disposable disposable = TmdbRepository.getInstance().getTrendingSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(series -> refreshData(mPopulares, adapterPopular, series.basicSeries));
        compositeDisposable.add(disposable);
    }

    private void refreshData(List<BasicResponse.SerieBasic> lista, HomeAdapter adapter, List<BasicResponse.SerieBasic> response) {
        lista.clear();
        lista.addAll(response);
        adapter.notifyDataSetChanged();
    }

    private void initRecycler(RecyclerView rv, HomeAdapter adapter) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter);
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
}