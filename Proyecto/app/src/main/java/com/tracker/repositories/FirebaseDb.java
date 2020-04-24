package com.tracker.repositories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tracker.models.serie.SerieResponse;

import java.util.List;

/**
 * Class that manages the connections to the Firebase Database, getting and uploading data
 */
public class FirebaseDb {

    private static FirebaseDb firebaseDb;
    private DatabaseReference mFavs;

    public static FirebaseDb getInstance() {
        if (firebaseDb == null) {
            firebaseDb = new FirebaseDb();
        }
        return firebaseDb;
    }

    private FirebaseDb() {
        mFavs = FirebaseDatabase.getInstance().getReference("favs");
    }

    public DatabaseReference getSeriesFav() {
        return mFavs;
    }

    public void setSeriesFav(List<SerieResponse.Serie> favs) {
        mFavs.setValue(favs);
    }

}
