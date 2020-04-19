package com.tracker.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tracker.R;
import com.tracker.adapters.TabLayoutAdapter;
import com.tracker.data.SeriesViewModel;

public class SearchFragment extends Fragment {

    private SeriesViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewPager(view);

        SearchView search = view.findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    searchViewModel.setQuery(query);
                } else {
                    searchViewModel.setQuery(getString(R.string.empty));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 0) {
                    searchViewModel.setQuery(query);
                } else {
                    searchViewModel.setQuery(getString(R.string.empty));
                }
                return true;
            }
        });

    }

    private void setViewPager(@NonNull View view) {
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabLayoutAdapter(this, true));
        String[] tabs = {getString(R.string.series), getString(R.string.people)};
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();
    }
}
