package com.tracker.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.tracker.R;
import com.tracker.models.people.PersonResponse;
import com.tracker.util.Util;

import java.time.LocalDate;
import java.time.Period;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tracker.util.Constants.BASE_URL_IMAGES_PORTRAIT;

public class FillActor {

    private final View mView;
    private final PersonResponse.Person mPerson;
    private final Context mContext;

    public FillActor(View view, PersonResponse.Person person, Context context) {
        this.mView = view;
        this.mPerson = person;
        this.mContext = context;
    }

    public void fillActor() {

        Toolbar actorName = mView.findViewById(R.id.toolbar_actor);
        CircleImageView actorPortrait = mView.findViewById(R.id.profile_image);
        View includeView = mView.findViewById(R.id.include_actor);

        TextView bornDate = includeView.findViewById(R.id.fecha_actor);
        TextView bornPlace = includeView.findViewById(R.id.lugar_actor);
        ReadMoreTextView biography = includeView.findViewById(R.id.bio_text);

        RecyclerView rvMovies = includeView.findViewById(R.id.rvPelis);
        RecyclerView rvSeries = includeView.findViewById(R.id.rvSeries);

        actorName.setTitle(mPerson.name);
        Util.getImagePortrait(BASE_URL_IMAGES_PORTRAIT + mPerson.profilePath, actorPortrait, mContext);

        bornDate.setText(calculateAge());
        bornPlace.setText(Util.checkNull(mPerson.placeOfBirth, mContext));
        biography.setText(Util.checkNull(mPerson.biography, mContext));

        initRecyclers(rvSeries);
        initRecyclers(rvMovies);
        setAdapters(rvSeries, rvMovies);
    }

    /**
     * Check that there's data in the recycler and if not, displays a message
     * @param rvSeries series' recyclerview
     * @param rvMovies films' recyclerView
     */
    private void setAdapters(RecyclerView rvSeries, RecyclerView rvMovies) {
        ViewSwitcher switcherFilms = mView.findViewById(R.id.switcherFilms);
        ViewSwitcher switcherSeries = mView.findViewById(R.id.switcherSeries);

        if (mPerson.tvCredits.cast.size() > 0) {
            rvSeries.setAdapter(new CastAdapter(mContext, mPerson.tvCredits.cast));
            if (R.id.rvSeries == switcherSeries.getNextView().getId()) {
                switcherSeries.showNext();
            }
        } else if (R.id.text_empty_dos == switcherSeries.getNextView().getId()) {
            switcherSeries.showNext();
        }

        if (mPerson.movieCredits.cast.size() > 0) {
            rvMovies.setAdapter(new CastAdapter(mContext, mPerson.movieCredits.cast, true));
            if (R.id.rvPelis == switcherFilms.getNextView().getId()) {
                switcherFilms.showNext();
            }
        } else if (R.id.text_empty_uno == switcherFilms.getNextView().getId()) {
            switcherFilms.showNext();
        }
    }

    /**
     * Initialize the recyclerView configuration
     * @param rv recyclerView
     */
    private void initRecyclers(RecyclerView rv) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }


    /**
     * Calculate age if date of birth is available
     * @return the actor's age
     */
    private String calculateAge() {
        if (mPerson.birthday != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                String[] fecha = mPerson.birthday.split("-");
                LocalDate birthday = LocalDate.of(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[1]));
                Period p = Period.between(birthday, today);
                return String.format("%s (%s %s)", mPerson.birthday, p.getYears(), mContext.getString(R.string.years));
            }
            return mPerson.birthday;
        }
        return mContext.getString(R.string.no_data);
    }
}
