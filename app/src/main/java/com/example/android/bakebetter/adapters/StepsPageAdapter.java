package com.example.android.bakebetter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.model.Step;

import java.util.List;

public class StepsPageAdapter extends FragmentStatePagerAdapter {
    private List<Step> stepsList;

    public StepsPageAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        this.stepsList = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeDetailsFragment.newInstance(stepsList.get(position));
    }

    @Override
    public int getCount() {
        return stepsList.size();
    }
}
