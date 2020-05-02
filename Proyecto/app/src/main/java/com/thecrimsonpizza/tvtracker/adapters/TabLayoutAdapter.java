package com.thecrimsonpizza.tvtracker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thecrimsonpizza.tvtracker.ui.HomeFragment;
import com.thecrimsonpizza.tvtracker.ui.search.ActorSearchFragment;
import com.thecrimsonpizza.tvtracker.ui.search.SerieSearchFragment;
import com.thecrimsonpizza.tvtracker.ui.series.CastFragment;
import com.thecrimsonpizza.tvtracker.ui.series.SeasonFragment;
import com.thecrimsonpizza.tvtracker.ui.series.SinopsisFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Adapter for the TabLayoutAdapter in SerieFragment and SearchFragment
 */
public class TabLayoutAdapter extends FragmentStateAdapter {

    private boolean isSearch;

    public TabLayoutAdapter(@NonNull Fragment fragment, boolean search) {
        super(fragment);
        this.isSearch = search;
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if (isSearch) {
            if (position == 0) return new SerieSearchFragment();
            else if (position == 1) return new ActorSearchFragment();
        } else {
            if (position == 0) return new SinopsisFragment();
            else if (position == 1) return new CastFragment();
            else if (position == 2) return new SeasonFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        if (isSearch) return 2;
        return 3;
    }
}
