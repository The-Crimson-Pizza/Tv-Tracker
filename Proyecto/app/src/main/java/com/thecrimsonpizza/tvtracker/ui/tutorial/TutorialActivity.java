package com.thecrimsonpizza.tvtracker.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.thecrimsonpizza.tvtracker.R;
import com.thecrimsonpizza.tvtracker.ui.LoginActivity;

public class TutorialActivity extends AppIntro2 {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_slide1));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_slide2));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_slide3));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_slide4));
        addSlide(IntroductionFragment.newInstance());

        setImmersiveMode();
        setSystemBackButtonLocked(true);
        setProgressIndicator();
        setIndicatorColor(getColor(R.color.bgTotal), getColor(R.color.colorPrimary));
    }

    @Override
    protected void onSkipPressed(@org.jetbrains.annotations.Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onDonePressed(@org.jetbrains.annotations.Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
