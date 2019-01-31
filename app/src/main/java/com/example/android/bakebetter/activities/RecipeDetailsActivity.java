package com.example.android.bakebetter.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.fragments.RecipeDetailsFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeDetailsActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "RecipeDetailsActivity";
    private static final String ARG_STEP_ID = "step";
    private int mStepId;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        AndroidInjection.inject(this);
        mStepId = getIntent().getIntExtra(ARG_STEP_ID, 0);
        Log.i(TAG, "step id = " + mStepId);
        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(mStepId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentDetailsContainer, detailsFragment)
                .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
