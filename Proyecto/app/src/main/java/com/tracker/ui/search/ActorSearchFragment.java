package com.tracker.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.actor.PersonResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


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
        return inflater.inflate(R.layout.fragment_actor_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);

        rvCast = view.findViewById(R.id.rvActores);
        rvCast.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvCast.setHasFixedSize(true);
        rvCast.setItemViewCacheSize(20);
        rvCast.setSaveEnabled(true);

        LiveData<String> query = model.getQuery();
        query.observe(getViewLifecycleOwner(), this::getResults);
    }

    private void getResults(String query) {
        RepositoryAPI.getInstance().searchPerson(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // No hacer nada
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull PersonResponse personResponse) {
                        mListaPersonas = personResponse.results;
                        if (!mListaPersonas.isEmpty()) {
                            searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
                            rvCast.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mListaPersonas = new ArrayList<>();
                        searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
                        rvCast.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }
}
