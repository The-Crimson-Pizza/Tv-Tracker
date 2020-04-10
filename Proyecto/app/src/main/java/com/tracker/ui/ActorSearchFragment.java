package com.tracker.ui;

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

import com.tracker.R;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.people.PersonResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.TAG;


public class ActorSearchFragment extends Fragment {

    private SeriesViewModel model;
    private List<PersonResponse.Person> mListaPersonas;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        return inflater.inflate(R.layout.fragment_actor_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv = view.findViewById(R.id.prueba);
        LiveData<String> query = model.getQuery();
        query.observe(getViewLifecycleOwner(), q -> {
            if (q.length() > 2) {
                getResults(q);
            }
            tv.setText(q);
        });
    }

    private void getResults(String query) {
        RepositoryAPI.getInstance().searchPerson(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lista -> {
                    mListaPersonas = lista.results;
                    if (mListaPersonas.size() > 0) {
                        tv.setText(mListaPersonas.get(0).name);
                    }
                    // todo - rellenar recycler
                });
    }
}
