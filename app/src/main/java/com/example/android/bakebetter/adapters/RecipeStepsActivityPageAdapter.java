package com.example.android.bakebetter.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.fragments.RecipeIngredientsFragment;
import com.example.android.bakebetter.fragments.RecipeStepListFragment;
import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.model.Step;

import java.util.ArrayList;

public class RecipeStepsActivityPageAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;
    private static final String ID_KEY = "id";
    private final Context mContext;
    private ArrayList<Step> mSteps;
    private ArrayList<Ingredient> mIngredients;

    public RecipeStepsActivityPageAdapter(Context context, FragmentManager fragmentManager,
                                          ArrayList<Step> steps, ArrayList<Ingredient> ingredients) {
        super(fragmentManager);
        this.mContext = context;
        this.mSteps = steps;
        this.mIngredients = ingredients;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = RecipeIngredientsFragment.newInstance(mIngredients);
                break;
            case 1:
                fragment = RecipeStepListFragment.newInstance(mSteps);
                break;
            default:
                fragment = RecipeStepListFragment.newInstance(mSteps);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getString(R.string.ingredients_tab_title);
            case 1:
                return mContext.getString(R.string.steps_tab_title);
            default:
                return null;
        }
    }
}
