package com.tracker.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.tracker.adapters.FillActor;
import com.tracker.data.RepositoryAPI;
import com.tracker.models.actor.PersonResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.tracker.util.Constants.BASE_URL_INSTAGRAM;
import static com.tracker.util.Constants.BASE_URL_INSTAGRAM_U;
import static com.tracker.util.Constants.BASE_URL_WEB_PERSON;
import static com.tracker.util.Constants.BASE_URL_WEB_SERIE;
import static com.tracker.util.Constants.ID_ACTOR;

public class ActorFragment extends Fragment {

    private int idActor;
    private PersonResponse.Person mActor;
    private Context mContext;
    private MenuItem itemInsta;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        itemInsta = toolbar.getMenu().findItem(R.id.action_insta);
        itemInsta.setVisible(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_share) {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, mActor.name);
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, BASE_URL_WEB_PERSON + mActor.id);
                    sendIntent.setType("text/plain");

                    startActivity(Intent.createChooser(sendIntent, null));


                    return true;
                } else if (item.getItemId() == R.id.action_insta) {
                    Uri uri = Uri.parse(BASE_URL_INSTAGRAM_U + mActor.externalIds.instagramId);
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.instagram.android");
                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL_INSTAGRAM + mActor.externalIds.instagramId)));
                    }

                    return true;
                }
                return false;
            }
        });

        FloatingActionButton fabFavorito = view.findViewById(R.id.fab);
        fabFavorito.setOnClickListener(view1 -> {
            Snackbar.make(view1, "Added to favourites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        });
        getActor(view);
    }

    private void getActor(View view) {
        RepositoryAPI.getInstance().getPerson(idActor)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(actor -> {
                    mActor = actor;
                    new FillActor(view, mActor, mContext).fillActor();
                    if (mActor.externalIds.instagramId != null && mActor.externalIds.instagramId.length() > 0) {
                        itemInsta.setVisible(true);
                    }
                });
    }
}
