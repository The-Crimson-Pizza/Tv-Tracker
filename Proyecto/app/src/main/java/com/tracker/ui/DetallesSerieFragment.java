package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import static com.tracker.util.Constants.TAG;

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
//        toolbar.setTitle("Toy Story 4");
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_series_to_actores));

        View include = view.findViewById(R.id.detallesSerie);
        ViewPager2 viewPager = include.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SeriesTabAdapter(this));
        String[] tabs = {"Sinopsis", "Reparto","Temporadas"};
        TabLayout tabLayout = include.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();

        if (idSerie != 0) {
            model = new ViewModelProvider(getActivity()).get(SerieViewModel.class);
            LiveData<Serie> s = model.getCurrentSerie(view, idSerie, getActivity());
            s.observe(getViewLifecycleOwner(), new Observer<Serie>() {
                @Override
                public void onChanged(Serie serie) {
                    mSerie = serie;
                }
            });

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                }
            });

//            new RepositoryAPI().getSerie(view, idSerie, getActivity());
//            new RepositoryAPI().getPerson(38940, new ArrayList<>(), getActivity());
        }
    }

}
