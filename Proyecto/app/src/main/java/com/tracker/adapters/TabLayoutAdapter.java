package com.tracker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tracker.ui.ActorSearchFragment;
import com.tracker.ui.CastFragment;
import com.tracker.ui.EpisodesFragment;
import com.tracker.ui.FavoritosFragment;
import com.tracker.ui.ProfileFragment;
import com.tracker.ui.SerieSearchFragment;
import com.tracker.ui.SinopsisFragment;

import org.jetbrains.annotations.NotNull;

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
            if (position == 0) {
                return new SerieSearchFragment();
            } else if (position == 1) {
                return new ActorSearchFragment();
            }
        } else {
            if (position == 0) {
                return new SinopsisFragment();
            } else if (position == 1) {
                return new CastFragment();
            } else if (position == 2) {
                return new EpisodesFragment();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (isSearch) {
            return 2;
        }
        return 3;
    }
}
