package com.tracker.repositories;

import com.google.firebase.auth.FirebaseUser;
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

    public static FirebaseDb getInstance(FirebaseUser currentUser) {
        if (firebaseDb == null) {
            firebaseDb = new FirebaseDb(currentUser);
        }
        return firebaseDb;
    }

    private FirebaseDb(FirebaseUser currentUser) {
        mFavs = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/favs");
    }

    public DatabaseReference getSeriesFav() {
        return mFavs;
    }

    public void setSeriesFav(List<SerieResponse.Serie> favs) {
        mFavs.setValue(favs);
    }

}
