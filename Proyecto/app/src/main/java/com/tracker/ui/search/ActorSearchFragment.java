package com.tracker.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.SearchAdapter;
import com.tracker.data.SeriesViewModel;
import com.tracker.data.TmdbRepository;
import com.tracker.models.actor.PersonResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ActorSearchFragment extends Fragment {

    private final List<PersonResponse.Person> mListaPersonas = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Context mContext;
    private RecyclerView rvCast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_actor_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewSwitcher switcherActor = view.findViewById(R.id.switcherSearchActor);
        SeriesViewModel model = new ViewModelProvider(requireActivity()).get(SeriesViewModel.class);
        searchAdapter = new SearchAdapter(mContext, null, mListaPersonas, false);
        rvCast = view.findViewById(R.id.rvActores);

        setRecycler();

        model.setQuery(getString(R.string.empty));
        LiveData<String> queryResult = model.getQuery();
        queryResult.observe(getViewLifecycleOwner(), query -> {
            if (query.isEmpty()) {
                if (R.id.search_image == switcherActor.getNextView().getId())
                    switcherActor.showNext();
            } else {
                if (R.id.rvActores == switcherActor.getNextView().getId())
                    switcherActor.showNext();
                getResults(query);
            }
        });
    }

    private void setRecycler() {
        rvCast.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvCast.setHasFixedSize(true);
        rvCast.setItemViewCacheSize(20);
        rvCast.setSaveEnabled(true);
        rvCast.setAdapter(searchAdapter);
    }

    private void getResults(String query) {
        TmdbRepository.getInstance().searchPerson(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mListaPersonas.clear();
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull PersonResponse personResponse) {
                        mListaPersonas.clear();
                        mListaPersonas.addAll(personResponse.results);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mListaPersonas.clear();
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }
}
