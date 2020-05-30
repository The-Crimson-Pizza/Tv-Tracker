package com.thecrimsonpizza.tvtracker.util;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thecrimsonpizza.tvtracker.ui.LoginActivity;
import com.thecrimsonpizza.tvtracker.ui.tutorial.TutorialFragment;

import static com.thecrimsonpizza.tvtracker.util.Constants.FIRST_OPENED;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean firstOpened = true;
        /*todo -  cambiar cuando acabe de configurar tutorial*/
//        boolean firstOpened = getPreferences(MODE_PRIVATE).getBoolean(FIRST_OPENED, true);

        if (firstOpened) {
            startActivity(new Intent(this, TutorialFragment.class));
            getPreferences(MODE_PRIVATE).edit().putBoolean(FIRST_OPENED, false).apply();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}