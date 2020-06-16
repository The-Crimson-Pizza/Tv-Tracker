package com.thecrimsonpizza.tvtracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thecrimsonpizza.tvtracker.data.FirebaseDb;
import com.thecrimsonpizza.tvtracker.models.seasons.Season;
import com.thecrimsonpizza.tvtracker.models.serie.SerieResponse;
import com.thecrimsonpizza.tvtracker.services.Receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.thecrimsonpizza.tvtracker.services.Receiver.ACTION_ALARM_RECEIVER;
import static com.thecrimsonpizza.tvtracker.util.Constants.SEASON_ID_EXTRA;
import static com.thecrimsonpizza.tvtracker.util.Constants.SEASON_NUMBER_EXTRA;
import static com.thecrimsonpizza.tvtracker.util.Constants.SERIE_NOMBRE_EXTRA;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private int startPos = 0;
    private int newPos;
    private List<SerieResponse.Serie> mFavs = new ArrayList<>();
    NotificationManager notificationManager;
    NotificationCompat.Builder summaryNotificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        startPos = 0;
        setNavigationView();

        getFavorites();
    }

    private void getFavorites() {
        FirebaseDb.getInstance(FirebaseAuth.getInstance().getCurrentUser()).getSeriesFav().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFavs.clear();
                GenericTypeIndicator<List<SerieResponse.Serie>> genericTypeIndicator = new GenericTypeIndicator<List<SerieResponse.Serie>>() {
                };
                List<SerieResponse.Serie> favTemp = dataSnapshot.getValue(genericTypeIndicator);
                if (favTemp != null) {
                    mFavs.addAll(favTemp);
                    for (SerieResponse.Serie serie : mFavs) {
                        if (!serie.seasons.isEmpty() && serie.seasons.get(serie.seasons.size() - 1).airDate != null) {
                            setAlarms(serie.seasons.get(serie.seasons.size() - 1), serie.name);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Sin implementar
            }
        });
    }


    private void setAlarms(Season season, String name) {

        boolean isAlarmActive = (getPendingIntent(season, name, PendingIntent.FLAG_NO_CREATE) != null);

        if (!isAlarmActive) {
            PendingIntent pendingIntent = getPendingIntent(season, name, PendingIntent.FLAG_UPDATE_CURRENT);

            int[] result = Arrays.stream(season.airDate.split("-")).mapToInt(Integer::parseInt).toArray();
            Date seasonDate = getSeasonDate(result);
            Date todayDate = getTodayDate();

            if (seasonDate.after(todayDate) || seasonDate.equals(todayDate)) {
//                try {
//                    pendingIntent.send();
//                } catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, seasonDate.getTime(), pendingIntent);
            }
        }
    }

    private PendingIntent getPendingIntent(Season season, String name, int flag) {
        return PendingIntent.getBroadcast(
                MainActivity.this,
                (int) System.currentTimeMillis(),
                new Intent(MainActivity.this, Receiver.class)
                        .setAction(ACTION_ALARM_RECEIVER + season.id)
                        .putExtra(SEASON_ID_EXTRA, season.id)
                        .putExtra(SERIE_NOMBRE_EXTRA, name)
                        .putExtra(SEASON_NUMBER_EXTRA, season.seasonNumber), flag);
    }

    private void cancelAlarm(Season season, AlarmManager alarmManager) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                season.id,
                new Intent(MainActivity.this, Receiver.class).setAction(Receiver.ACTION_ALARM_RECEIVER),
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private Date getTodayDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTime();
    }

    private Date getSeasonDate(int[] result) {
        Calendar seasonDate = new GregorianCalendar(result[0], result[1] - 1, result[2]);
        seasonDate.set(Calendar.HOUR_OF_DAY, 12);
        return seasonDate.getTime();
    }

    @Override
    public void onBackPressed() {
        startPos = 0;
        super.onBackPressed();
    }

    @SuppressWarnings("SameReturnValue")
    private void setNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    goToHome();
                    break;
                case R.id.navigation_search:
                    goToSearch();
                    break;
                case R.id.navigation_fav:
                    goToFavs();
                    break;
                case R.id.navigation_profile:
                    goToProfile();
                    break;
                default:
            }
            startPos = newPos;
            return true;
        });
    }

    private void goToProfile() {
        newPos = 3;
        if (startPos < newPos)
            navController.navigate(R.id.action_global_navigation_profile_left);
        else navController.navigate(R.id.action_global_navigation_profile);
    }

    private void goToFavs() {
        newPos = 2;
        if (startPos < newPos) navController.navigate(R.id.action_global_navigation_fav_left);
        else if (newPos < startPos)
            navController.navigate(R.id.action_global_navigation_fav_right);
        else navController.navigate(R.id.action_global_navigation_fav_left);
    }

    private void goToSearch() {
        newPos = 1;
        if (startPos < newPos)
            navController.navigate(R.id.action_global_navigation_search_to_left);
        else if (newPos < startPos)
            navController.navigate(R.id.action_global_navigation_search_to_right);
        else navController.navigate(R.id.action_global_navigation_search);
    }

    private void goToHome() {
        newPos = 0;
        if (startPos == newPos) navController.navigate(R.id.action_global_navigation_home);
        else navController.navigate(R.id.action_global_navigation_home_right);
    }
}