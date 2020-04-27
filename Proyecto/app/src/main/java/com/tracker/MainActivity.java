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
    private int startingPosition = 0;
    private int newPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        startingPosition = 0;
        setNavigationView();

    }

    @Override
    public void onBackPressed() {
        startingPosition = 0;
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
            startingPosition = newPosition;
            return true;
        });
    }

    private void goToProfile() {
        newPosition = 3;
        if (startingPosition < newPosition) {
            navController.navigate(R.id.action_global_navigation_profile);
        }
    }

    private void goToFavs() {
        newPosition = 2;
        if (startingPosition < newPosition) {
            navController.navigate(R.id.action_global_navigation_fav_left);
        } else if (newPosition < startingPosition) {
            navController.navigate(R.id.action_global_navigation_fav_right);
        } else {
            navController.navigate(R.id.action_global_navigation_fav_left);
        }
    }

    private void goToSearch() {
        newPosition = 1;
        if (startingPosition < newPosition) {
            navController.navigate(R.id.action_global_navigation_search_to_left);
        } else if (newPosition < startingPosition) {
            navController.navigate(R.id.action_global_navigation_search_to_right);
        }
    }

    private void goToHome() {
        newPosition = 0;
        if (startingPosition == newPosition) {
            navController.navigate(R.id.action_global_navigation_home);
        } else {
            navController.navigate(R.id.action_global_navigation_home_right);
        }
    }
}