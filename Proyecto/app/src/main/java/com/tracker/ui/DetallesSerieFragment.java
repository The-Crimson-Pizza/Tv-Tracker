package com.tracker.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetallesSerieFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_detalles_serie, container, false);

//        Toolbar toolbar = root.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Toy Story 4");
//        getActivity().getSupportFragmentManager().setTitle("Toy Story 4");

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            FragmentManager fManager = getParentFragmentManager();
            fManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.nav_host_fragment, new DetallesActorFragment())
                    .commit();

        });


        return root;
    }

}
