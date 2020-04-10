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

public class RellenarActor {

    private View mVista;
    private PersonResponse.Person mPerson;
    private Context mContext;

    public RellenarActor(View vista, PersonResponse.Person person, Context context) {
        this.mVista = vista;
        this.mPerson = person;
        this.mContext = context;
    }

    public void fillActor() {
        Toolbar nombreActor = mVista.findViewById(R.id.toolbar_actor);
        CircleImageView fotoActor = mVista.findViewById(R.id.profile_image);
        View include = mVista.findViewById(R.id.include_actor);

        TextView fecha = include.findViewById(R.id.fecha_actor);
        TextView lugar = include.findViewById(R.id.lugar_actor);
        ReadMoreTextView bio = include.findViewById(R.id.bio_text);
        RecyclerView rvPelis = include.findViewById(R.id.rvPelis);
        RecyclerView rvSeries = include.findViewById(R.id.rvSeries);

        nombreActor.setTitle(mPerson.name);
        new Util().getImageNoPlaceholder(BASE_URL_IMAGES_PORTRAIT + mPerson.profilePath, fotoActor, mContext);

        fecha.setText(calcularEdad());
        lugar.setText(mPerson.placeOfBirth);
        if (mPerson.biography.length() != 0) {
            bio.setText(mPerson.biography);
        } else {
            bio.setText("No biography");
        }

        setAdapters(rvSeries);
        setAdapters(rvPelis);

        rvSeries.setAdapter(new ActorCastAdapter(mContext, mPerson, false));
        rvPelis.setAdapter(new ActorCastAdapter(mContext, mPerson, true));
    }

    void setAdapters(RecyclerView rv) {
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setSaveEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    String calcularEdad() {
        if (mPerson.birthday.length() != 0) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                String[] fecha = mPerson.birthday.split("-");
                LocalDate birthday = LocalDate.of(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[1]));
                Period p = Period.between(birthday, today);
                return String.format("%s (%s %s)", mPerson.birthday, p.getYears(), mContext.getString(R.string.years));
            }
            return mPerson.birthday;
        }
        return "No data";
    }
}
