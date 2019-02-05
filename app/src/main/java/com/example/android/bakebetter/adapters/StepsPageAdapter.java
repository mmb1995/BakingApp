package com.example.android.bakebetter.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.model.Step;

import java.util.List;

public class StepsPageAdapter extends FragmentStatePagerAdapter {
    private List<Step> stepsList;
    private SparseArray<RecipeDetailsFragment> mFragmentsCache = new SparseArray<>();

    public StepsPageAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        this.stepsList = steps;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        if (fragment instanceof  RecipeDetailsFragment) {
            mFragmentsCache.append(position, (RecipeDetailsFragment) fragment);
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragmentsCache.delete(position);
        super.destroyItem(container, position, object);
    }

    public RecipeDetailsFragment getCachedFragment(int position) {
        return mFragmentsCache.get(position);
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
