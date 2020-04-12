package com.tracker.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

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
        new Util().getImageNoPlaceholder(BASE_URL_IMAGES_PORTRAIT + mPerson.profilePath, actorPortrait, mContext);

        bornDate.setText(calculateAge());
        bornPlace.setText(new Util().checkExist(mPerson.placeOfBirth, mContext));
        biography.setText(new Util().checkExist(mPerson.biography, mContext));

        initRecyclers(rvSeries);
        initRecyclers(rvMovies);

        rvSeries.setAdapter(new CastAdapter(mContext, mPerson.tvCredits.cast));
        rvMovies.setAdapter(new CastAdapter(mContext, mPerson.movieCredits.cast, true));
    }

    private void initRecyclers(RecyclerView rv) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }



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
