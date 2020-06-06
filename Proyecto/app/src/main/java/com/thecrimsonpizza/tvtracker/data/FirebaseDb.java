package com.thecrimsonpizza.tvtracker.data;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;

import java.util.List;

/**
 * Class that manages the connections to the Firebase Database, getting and uploading data
 */
public class FirebaseDb {

    private static FirebaseDb firebaseDb;
    private final DatabaseReference mFavs;
    private final DatabaseReference isNew;

    public static FirebaseDb getInstance(FirebaseUser currentUser) {
        if (firebaseDb == null) {
            firebaseDb = new FirebaseDb(currentUser);
        }
        return firebaseDb;
    }

    private FirebaseDb(FirebaseUser currentUser) {
        mFavs = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/series_following");
        isNew = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/config/tutorial");
    }

    public DatabaseReference getSeriesFav() {
        return mFavs;
    }

    public DatabaseReference getIsNew() {
        return isNew;
    }

    public void setSeriesFav(List<SerieResponse.Serie> favs) {
        mFavs.setValue(favs);
    }

    public void setSeriesFav(Boolean nuevo) {
        isNew.setValue(nuevo);
    }

}
