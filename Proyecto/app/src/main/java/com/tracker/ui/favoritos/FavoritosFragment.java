package com.tracker.ui.favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tracker.R;

public class FavoritosFragment extends Fragment {

    private FavoritosViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);

        View root = inflater.inflate(R.layout.favoritos_fragment, container, false);

        return root;
    }

}
