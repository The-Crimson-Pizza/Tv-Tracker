package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.ActorBasicAdapter;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.people.PersonResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.tracker.util.Constants.TAG;


public class ActorSearchFragment extends Fragment {

    private SeriesViewModel model;
    private List<PersonResponse.Person> mListaPersonas;
    private SearchAdapter searchAdapter;
    private Context mContext;
    private RecyclerView rvCast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        return inflater.inflate(R.layout.fragment_actor_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCast = view.findViewById(R.id.rvActores);
        rvCast.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvCast.setHasFixedSize(true);
        rvCast.setItemViewCacheSize(20);
        rvCast.setSaveEnabled(true);

        LiveData<String> query = model.getQuery();
        query.observe(getViewLifecycleOwner(), q -> {
//            if (q.length() > 2) {
                getResults(q);
//            }
        });
    }

    private void getResults(String query) {
//        RepositoryAPI.getInstance().searchPerson(query)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(lista -> {
//                    mListaPersonas = lista.results;
//                    if (mListaPersonas.size() > 0) {
//                        searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
//                        rvCast.setAdapter(searchAdapter);
//                    }
//                },
//                        throwable -> Log.e(TAG, "Throwable " + throwable.getMessage())
//                );

        RepositoryAPI.getInstance().searchPerson(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull PersonResponse personResponse) {
                        mListaPersonas = personResponse.results;
                        if (mListaPersonas.size() > 0) {
                            searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
                            rvCast.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.e(TAG, "Throwable " + e.getMessage());
                        mListaPersonas = new ArrayList<>();
                        searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
                        rvCast.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
