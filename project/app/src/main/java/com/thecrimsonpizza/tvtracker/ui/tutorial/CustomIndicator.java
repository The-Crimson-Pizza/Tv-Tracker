package com.thecrimsonpizza.tvtracker.ui.tutorial;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.appintro.indicator.IndicatorController;
import com.thecrimsonpizza.tvtracker.R;

import org.jetbrains.annotations.NotNull;

class CustomIndicator implements IndicatorController {

    public final int primaryColor;
    public final int secondColor;
    private static final int FIRST_PAGE_NUM = 0;
    int selectedDotColor = 1;
    int unselectedDotColor = 1;
    private ProgressBar mProgressBar;

    public CustomIndicator(int primary, int secondary) {
        primaryColor = primary;
        secondColor = secondary;
    }

    @NotNull
    @Override
    public View newInstance(@NonNull Context context) {
        mProgressBar = (ProgressBar) View.inflate(context, R.layout.tutorial_progress_bar, null);
        if (selectedDotColor != primaryColor)
            mProgressBar.getProgressDrawable().setColorFilter(selectedDotColor, PorterDuff.Mode.SRC_IN);
        if (unselectedDotColor != primaryColor)
            mProgressBar.getIndeterminateDrawable().setColorFilter(unselectedDotColor, PorterDuff.Mode.SRC_IN);

        return mProgressBar;
    }

    @Override
    public void initialize(int slideCount) {
        mProgressBar.setMax(slideCount);
        selectPosition(FIRST_PAGE_NUM);
    }

    @Override
    public void selectPosition(int index) {
        mProgressBar.setProgress(index + 1);
        if (index % 2 == 0) {
            selectedDotColor = primaryColor;
            mProgressBar.getProgressDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
        } else {
            selectedDotColor = secondColor;
            mProgressBar.getProgressDrawable().setColorFilter(secondColor, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void setSelectedIndicatorColor(int color) {
        this.selectedDotColor = color;
        if (mProgressBar != null)
            mProgressBar.getProgressDrawable().setColorFilter(selectedDotColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setUnselectedIndicatorColor(int color) {
        this.unselectedDotColor = color;
        if (mProgressBar != null)
            mProgressBar.getIndeterminateDrawable().setColorFilter(unselectedDotColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getSelectedIndicatorColor() {
        return this.selectedDotColor;
    }

    @Override
    public int getUnselectedIndicatorColor() {
        return this.unselectedDotColor;
    }
}
