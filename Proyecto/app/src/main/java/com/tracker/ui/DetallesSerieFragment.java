package com.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tracker.R;
import com.tracker.adapters.SeriesTabAdapter;
import com.tracker.models.SerieViewModel;
import com.tracker.models.series.Serie;

import static com.tracker.util.Constants.ID_SERIE;

public class DetallesSerieFragment extends Fragment {

    private SerieViewModel model;
    private int idSerie = 0;
    private Serie mSerie;

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
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_series_to_actores));

        View include = view.findViewById(R.id.detallesSerie);
        ViewPager2 viewPager = include.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SeriesTabAdapter(this));
        String[] tabs = {getString(R.string.sinopsis), getString(R.string.reparto), getString(R.string.temporadas)};
        TabLayout tabLayout = include.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();

        if (idSerie != 0) {
            model = new ViewModelProvider(getActivity()).get(SerieViewModel.class);
            LiveData<Serie> s = model.getSerie(view, idSerie, getActivity());
            s.observe(getViewLifecycleOwner(), serie -> mSerie = serie);

        }
    }
}
