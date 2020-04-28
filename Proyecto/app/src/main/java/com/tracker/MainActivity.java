package com.tracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private int startPos = 0;
    private int newPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        startPos = 0;
        setNavigationView();
    }

    @Override
    public void onBackPressed() {
        startPos = 0;
        super.onBackPressed();
    }


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
        if (startPos < newPos) {
            navController.navigate(R.id.action_global_navigation_profile_left);
        } else {
            navController.navigate(R.id.action_global_navigation_profile);
        }
    }

    private void goToFavs() {
        newPos = 2;
        if (startPos < newPos) {
            navController.navigate(R.id.action_global_navigation_fav_left);
        } else if (newPos < startPos) {
            navController.navigate(R.id.action_global_navigation_fav_right);
        } else {
            navController.navigate(R.id.action_global_navigation_fav_left);
        }
    }

    private void goToSearch() {
        newPos = 1;
        if (startPos < newPos) {
            navController.navigate(R.id.action_global_navigation_search_to_left);
        } else if (newPos < startPos) {
            navController.navigate(R.id.action_global_navigation_search_to_right);
        } else {
            navController.navigate(R.id.action_global_navigation_search);
        }
    }

    private void goToHome() {
        newPos = 0;
        if (startPos == newPos) {
            navController.navigate(R.id.action_global_navigation_home);
        } else {
            navController.navigate(R.id.action_global_navigation_home_right);
        }
    }
}