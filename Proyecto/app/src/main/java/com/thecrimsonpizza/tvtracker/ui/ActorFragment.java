package com.thecrimsonpizza.tvtracker.ui;

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

import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.adapters.FillActor;
import com.thecrimsonpizza.tvtracker.data.TmdbRepository;
import com.thecrimsonpizza.tvtracker.models.actor.PersonResponse;
import com.thecrimsonpizza.tvtracker.util.Constants;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class ActorFragment extends Fragment {

    private int idActor;
    private PersonResponse.Person mActor;
    private Context mContext;
    private MenuItem itemInsta;

    private CompositeDisposable compositeDisposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
        if (getArguments() != null) {
            idActor = getArguments().getInt(Constants.ID_ACTOR);
        }
        return inflater.inflate(R.layout.fragment_actor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar_actor);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        itemInsta = toolbar.getMenu().findItem(R.id.action_insta);
        itemInsta.setVisible(false);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                shareContent();
                return true;
            } else if (item.getItemId() == R.id.action_insta) {
                goToInstagram();
                return true;
            }
            return false;
        });

        getActor(view);
    }

    private void goToInstagram() {
        Uri uri = Uri.parse(Constants.BASE_URL_INSTAGRAM_U + mActor.externalIds.instagramId);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage(Constants.COM_INSTAGRAM_ANDROID);
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(mContext, WebViewActivity.class)
                    .putExtra(Constants.URL_WEBVIEW, Constants.BASE_URL_INSTAGRAM + mActor.externalIds.instagramId));
        }
    }

    private void shareContent() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, mActor.name);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sharing));
        sendIntent.putExtra(Intent.EXTRA_TEXT, Constants.BASE_URL_WEB_PERSON + mActor.id);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, null));
    }

    private void getActor(View view) {
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = TmdbRepository.getInstance().getPerson(idActor)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(actor -> {
                    mActor = actor;
                    new FillActor(view, mActor, mContext).fillActor();
                    if (mActor.externalIds.instagramId != null && mActor.externalIds.instagramId.length() > 0) {
                        itemInsta.setVisible(true);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
