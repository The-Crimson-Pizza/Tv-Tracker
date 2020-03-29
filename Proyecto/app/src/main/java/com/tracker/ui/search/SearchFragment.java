package com.tracker.ui.search;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.R;
import com.tracker.ui.favoritos.FavoritosViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.search_fragment, container, false);


        return root;
    }

}
