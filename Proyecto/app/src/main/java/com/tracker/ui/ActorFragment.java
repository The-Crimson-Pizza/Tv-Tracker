package com.tracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tracker.R;
import com.tracker.adapters.RellenarActor;
import com.tracker.data.RepositoryAPI;
import com.tracker.models.people.Person;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.ID_ACTOR;

public class ActorFragment extends Fragment {

    private int idActor;
    private Person mActor;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            idActor = getArguments().getInt(ID_ACTOR);
        }
        return inflater.inflate(R.layout.fragment_actor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = view.findViewById(R.id.toolbar_actor);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        FloatingActionButton fabFavorito = view.findViewById(R.id.fab);
        fabFavorito.setOnClickListener(view1 -> {
            Snackbar.make(view1, "Added to favourites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", null).show();
        });

        getActor(view);

        // TODO IMPLEMENTAR BOTONES REDES SOCIALES

    }

    private void getActor(View view) {
        RepositoryAPI.getInstance().getPerson(idActor)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(actor -> {
                    mActor = actor;
                    new RellenarActor(view, mActor, mContext).fillActor();
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detalles_actor, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        } else if (id == R.id.action_wiki) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
