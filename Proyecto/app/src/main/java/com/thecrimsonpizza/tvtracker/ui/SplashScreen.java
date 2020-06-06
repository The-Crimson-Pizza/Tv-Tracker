package com.thecrimsonpizza.tvtracker.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thecrimsonpizza.tvtracker.ui.tutorial.TutorialFragment;

import static com.thecrimsonpizza.tvtracker.util.Constants.FIRST_OPENED;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        boolean firstOpened = true;
        boolean firstOpened = getPreferences(MODE_PRIVATE).getBoolean(FIRST_OPENED, true);

        if (firstOpened) {
            getPreferences(MODE_PRIVATE).edit().putBoolean(FIRST_OPENED, false).apply();
            startActivity(new Intent(this, TutorialFragment.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}