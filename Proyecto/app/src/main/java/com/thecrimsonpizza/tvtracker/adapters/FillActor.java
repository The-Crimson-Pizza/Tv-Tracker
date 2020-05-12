package com.thecrimsonpizza.tvtracker.adapters;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.models.actor.PersonResponse;
import com.thecrimsonpizza.tvtracker.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thecrimsonpizza.tvtracker.util.Constants.BASE_URL_IMAGES_PORTRAIT;
import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_DEFAULT;
import static com.thecrimsonpizza.tvtracker.util.Constants.FORMAT_LONG;

public class FillActor {

    private final View mView;
    private final PersonResponse.Person mPerson;
    private final Context mContext;

    public FillActor(View view, PersonResponse.Person person, Context context) {
        this.mView = view;
        this.mPerson = person;
        this.mContext = context;
    }

    /**
     * Fills the data obtained by the api in the ActorFragment
     */
    public void fillActor() {

        Toolbar actorName = mView.findViewById(R.id.toolbar_actor);
        CircleImageView actorPortrait = mView.findViewById(R.id.profile_image);
        View includeView = mView.findViewById(R.id.include_actor);

        TextView bornDate = includeView.findViewById(R.id.fecha_actor);
        TextView bornPlace = includeView.findViewById(R.id.lugar_actor);
        TextView deathDate = includeView.findViewById(R.id.fecha_actor_mortimer);
        ImageView deathDateIcon = includeView.findViewById(R.id.icon_mortimer);
        ReadMoreTextView biography = includeView.findViewById(R.id.bio_text);

        RecyclerView rvMovies = includeView.findViewById(R.id.rv_movies);
        RecyclerView rvSeries = includeView.findViewById(R.id.rvSeries);

        actorName.setTitle(mPerson.name);
        Util.getImagePortrait(BASE_URL_IMAGES_PORTRAIT + mPerson.profilePath, actorPortrait, mContext);

        if (mPerson.isDead()) {
            deathDate.setText(calculateAge(true));
            deathDate.setVisibility(View.VISIBLE);
            deathDateIcon.setVisibility(View.VISIBLE);
        }

        if (mPerson.birthday != null) {
            bornDate.setText(calculateAge(false));
        } else {
            bornDate.setText(mContext.getString(R.string.no_data));
        }

        bornPlace.setText(Util.checkNull(mPerson.placeOfBirth, mContext));
        biography.setText(Util.checkNull(mPerson.biography, mContext));

        initRecyclers(rvSeries);
        initRecyclers(rvMovies);
        setAdapters(rvSeries, rvMovies);
    }

    /**
     * Check that there's data in the recycler and if not, displays a message
     *
     * @param rvSeries series' recyclerview
     * @param rvMovies films' recyclerView
     */
    private void setAdapters(RecyclerView rvSeries, RecyclerView rvMovies) {
        ViewSwitcher switcherFilms = mView.findViewById(R.id.switcherFilms);
        ViewSwitcher switcherSeries = mView.findViewById(R.id.switcherSeries);

        if (!mPerson.tvCredits.cast.isEmpty()) {
            rvSeries.setAdapter(new PeopleCreditsAdapter(mContext, mPerson.tvCredits.cast));
            if (R.id.rvSeries == switcherSeries.getNextView().getId()) {
                switcherSeries.showNext();
            }
        } else if (R.id.text_empty_dos == switcherSeries.getNextView().getId()) {
            switcherSeries.showNext();
        }

        if (!mPerson.movieCredits.cast.isEmpty()) {
            rvMovies.setAdapter(new PeopleCreditsAdapter(mContext, mPerson.movieCredits.cast, true));
            if (R.id.rv_movies == switcherFilms.getNextView().getId()) {
                switcherFilms.showNext();
            }
        } else if (R.id.text_empty_uno == switcherFilms.getNextView().getId()) {
            switcherFilms.showNext();
        }
    }

    /**
     * Initialize the recyclerView configuration
     *
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
     *
     * @return the actor's age
     */
    private String calculateAge(boolean dead) {
        LocalDate bornDate;
        LocalDate deathDate;
        String bornDateNew;
        String deathDateNew = "";
        Period period;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bornDate = parseStringToLocalDate(mPerson.birthday);

            if (mPerson.deathday != null) {
                deathDate = parseStringToLocalDate(mPerson.deathday);
                period = Period.between(bornDate, deathDate);
                bornDateNew = String.format("%s", Util.convertStringDateFormat(mPerson.birthday, FORMAT_LONG));
                deathDateNew = String.format("%s (%s %s)", Util.convertStringDateFormat(mPerson.deathday, FORMAT_LONG), period.getYears(), mContext.getString(R.string.years));
            } else {
                period = Period.between(bornDate, LocalDate.now());
                bornDateNew = String.format("%s (%s %s)", Util.convertStringDateFormat(mPerson.birthday, FORMAT_LONG), period.getYears(), mContext.getString(R.string.years));
            }

            if (dead) {
                return deathDateNew;
            } else {
                return bornDateNew;
            }

        } else {
            if (dead) {
                return mPerson.deathday;
            } else {
                return mPerson.birthday;
            }
        }
    }


    /**
     * Parses a string to LocalDate format
     *
     * @param oldDate date in String
     * @return oldDate in LocalDate
     */
    private LocalDate parseStringToLocalDate(String oldDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
            formatter = formatter.withLocale(Locale.getDefault());
            return LocalDate.parse(oldDate, formatter);
        } else {
            return null;
        }
    }
}
