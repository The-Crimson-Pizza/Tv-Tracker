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
    private final DatabaseReference tempIds;

    public static FirebaseDb getInstance(FirebaseUser currentUser) {
        if (firebaseDb == null) {
            firebaseDb = new FirebaseDb(currentUser);
        }
        return firebaseDb;
    }

    private FirebaseDb(FirebaseUser currentUser) {
        mFavs = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/series_following");
        tempIds = FirebaseDatabase.getInstance().getReference("temp/");
//        tempIds = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/config/tutorial");
    }

    public DatabaseReference getSeriesFav() {
        return mFavs;
    }

    public void setSeriesFav(List<SerieResponse.Serie> favs) {
        mFavs.setValue(favs);
    }

    public DatabaseReference getTempIds() {
        return tempIds;
    }

    public void setTempIds(List<Integer> tempIdset) {
        mFavs.setValue(tempIdset);
    }

}
