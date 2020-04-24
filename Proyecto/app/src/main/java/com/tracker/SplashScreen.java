package com.tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//We will use the following code to load Splash Screen first then move it to Main Activity
        startActivity(new Intent(this, MainActivity.class));
    }
}