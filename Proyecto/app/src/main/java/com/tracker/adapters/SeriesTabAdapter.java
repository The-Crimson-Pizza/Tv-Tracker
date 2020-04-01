package com.tracker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tracker.ui.FavoritosFragment;
import com.tracker.ui.SearchFragment;
import com.tracker.ui.SinopsisFragment;

public class SeriesTabAdapter extends FragmentStateAdapter {


    public SeriesTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SinopsisFragment();
        } else if (position == 1) {
            return new SearchFragment();
        } else if (position == 2) {
            return new FavoritosFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
