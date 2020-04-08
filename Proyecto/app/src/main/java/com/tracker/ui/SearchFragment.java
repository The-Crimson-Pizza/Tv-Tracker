package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tracker.R;

public class SearchFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TODO - PANTALLA DE BUSCAR

        //        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Log.d(Constants.TAG, query);
//        }
    }
}
