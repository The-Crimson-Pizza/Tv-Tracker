package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tracker.R;
import com.tracker.adapters.FavoritesAdapter;
import com.tracker.data.FirebaseDb;
import com.tracker.models.SerieFav;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {

    private Context mContext;
    private DatabaseReference databaseRef;
    private List<SerieFav> mFavs = new ArrayList<>();
    private FavoritesAdapter favAdapter;
    private RecyclerView rvFavs;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favAdapter = new FavoritesAdapter(getActivity(), mFavs);
        rvFavs = view.findViewById(R.id.grid_favoritas);

        rvFavs.setHasFixedSize(true);
        rvFavs.setItemViewCacheSize(20);
        rvFavs.setSaveEnabled(true);
        rvFavs.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvFavs.setAdapter(favAdapter);

        FirebaseDb.getInstance().getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                GenericTypeIndicator<List<SerieFav>> genericTypeIndicator = new GenericTypeIndicator<List<SerieFav>>() {
                };
                List<SerieFav> favTemp = dataSnapshot.getValue(genericTypeIndicator);
                if (favTemp != null) {
                    mFavs.addAll(favTemp);
                    favAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
