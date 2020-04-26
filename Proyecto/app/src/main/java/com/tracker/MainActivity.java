package com.tracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController navController;
    int startingPosition = 0;
    int newPosition;

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

    private void setNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    newPosition = 0;
                    if (startingPosition == newPosition) {
                        navController.navigate(R.id.action_global_navigation_home);
                    } else {
                        navController.navigate(R.id.action_global_navigation_home_right);
                    }
                    break;
                case R.id.navigation_search:
                    newPosition = 1;
                    if (startingPosition < newPosition) {
                        navController.navigate(R.id.action_global_navigation_search_to_left);
                    } else if (newPosition < startingPosition) {
                        startingPosition = R.id.navigation_search;
                        navController.navigate(R.id.action_global_navigation_search_to_right);
                    }
                    break;
                case R.id.navigation_fav:
                    newPosition = 2;
                    if (startingPosition < newPosition) {
                        navController.navigate(R.id.action_global_navigation_fav_left);
                    } else if (newPosition < startingPosition) {
                        navController.navigate(R.id.action_global_navigation_fav_right);
                    }
                    break;
                case R.id.navigation_profile:
                    newPosition = 3;
                    if (startingPosition < newPosition) {
                        navController.navigate(R.id.action_global_navigation_profile);
                    }
                    break;
                default:
                    return true;
            }
            startingPosition = newPosition;
            return true;
        });
    }
}