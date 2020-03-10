package com.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tracker.ui.prueba.PruebaFragment;

public class Prueba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PruebaFragment.newInstance())
                    .commitNow();
        }
    }
}
