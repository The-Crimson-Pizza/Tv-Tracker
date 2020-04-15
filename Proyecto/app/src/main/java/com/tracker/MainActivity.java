package com.tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tracker.ui.FavoritosFragment;
import com.tracker.ui.HomeFragment;
import com.tracker.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController navController;
    int startingPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        startingPosition = R.id.navigation_home;

        NavOptions options = new NavOptions.Builder()
//                .setEnterAnim(R.animator.slide_in_left)
//                .setExitAnim(R.anim.default_exit_anim)
//                .setPopEnterAnim(R.anim.default_pop_enter_anim)
//                .setPopExitAnim(R.anim.default_pop_exit_anim)
                .setPopUpTo(navController.getCurrentDestination().getId(), true)
                .build();
//        navController.navigate(R.id.action_global_navigation_home, null, options)

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        startingPosition = R.id.navigation_home;
//                            navController.navigate(R.id.action_global_navigation_home, null, options);
                        navController.navigate(R.id.action_global_navigation_home);
                        break;
                    case R.id.navigation_search:
                        if (startingPosition != R.id.navigation_search) {
                            startingPosition = R.id.navigation_search;
                            navController.navigate(R.id.action_global_navigation_search);
                        }
                        break;
                    case R.id.navigation_fav:
                        if (startingPosition != R.id.navigation_fav) {
                            startingPosition = R.id.navigation_fav;
                            navController.navigate(R.id.action_global_navigation_fav);
                        }
                        break;
                    case R.id.navigation_profile:
                        if (startingPosition != R.id.navigation_profile) {
                            startingPosition = R.id.navigation_profile;
                            navController.navigate(R.id.action_global_navigation_profile);
                        }
                        break;
                }

                return true;
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        int selectedItemId = bottomNavigationView.getSelectedItemId();
//        if (R.id.navigation_home != selectedItemId) {
//            loadFragment(mFirstFragment, 1);
//            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//        } else {
//            super.onBackPressed();
//        }
//    }


//    @Override
//    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
//        super.onOptionsItemSelected(item);
//        return false;
//    }


}
