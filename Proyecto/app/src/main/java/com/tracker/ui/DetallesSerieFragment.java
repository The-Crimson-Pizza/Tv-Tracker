package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tracker.R;
import com.tracker.controllers.RepositoryAPI;
import com.tracker.models.series.Serie;

import java.util.ArrayList;
import java.util.List;

import static com.tracker.util.Constants.ID_SERIE;

public class DetallesSerieFragment extends Fragment {

    private int idSerie = 0;
    private List<Serie> lista = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idSerie = getArguments().getInt(ID_SERIE);
        }

        return inflater.inflate(R.layout.activity_detalles_serie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Toy Story 4");
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_series_to_actores));

        if (idSerie != 0) {
            new RepositoryAPI().getSerie(view, idSerie, getActivity());
//            new RepositoryAPI().getPerson(38940, new ArrayList<>(), getActivity());
        }
    }
}
