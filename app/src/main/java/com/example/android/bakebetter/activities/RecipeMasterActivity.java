package com.example.android.bakebetter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeStepsActivityPageAdapter;
import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.fragments.RecipeIngredientsFragment;
import com.example.android.bakebetter.fragments.RecipeStepListFragment;
import com.example.android.bakebetter.model.Step;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeMasterActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        RecipeStepListFragment.OnStepSelectedListener {
    private static final String TAG = "RecipeMasterActivity";

    public static final String ARG_RECIPE_ID = "recipe";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Nullable
    @BindView(R.id.recipe_details_tabs)
    TabLayout mTabLayout;
    @Nullable
    @BindView(R.id.recipe_viewpager)
    ViewPager mViewPager;
    @Nullable
    @BindView(R.id.ingredientsButton)
    Button mIngredientsButton; // Only for tablet will be null otherwise

    private Long mRecipeId;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        // Get the data passed in from the starting intent
        this.mRecipeId = getIntent().getLongExtra(ARG_RECIPE_ID, 0);

        // Check for phone type
        if (getResources().getBoolean(R.bool.isTablet)) {
            mTwoPane = true;
            Objects.requireNonNull(mIngredientsButton).setOnClickListener(view -> addIngredientsFragment());

            // Check if this is the first run
            if (savedInstanceState == null) {
                addIngredientsFragment();
                addStepsFragments();
            }

        } else {
            // Phone is not a tablet
            setUpViewPager();
        }
    }

    private void addIngredientsFragment() {
        RecipeIngredientsFragment ingredientsFragment = RecipeIngredientsFragment.newInstance(mRecipeId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // hide ingredients button
        Objects.requireNonNull(mIngredientsButton).setVisibility(View.INVISIBLE);

        // check if there is already a fragment present
        if (fragmentManager.findFragmentById(R.id.detailsFrameLayout) == null) {
            transaction.add(R.id.detailsFrameLayout, ingredientsFragment);
        } else {
            transaction.replace(R.id.detailsFrameLayout, ingredientsFragment);
        }

        transaction.commit();
    }


    private void addStepsFragments() {
        RecipeStepListFragment stepListFragment = RecipeStepListFragment.newInstance(mRecipeId);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.stepsFrameLayout, stepListFragment)
                .commit();
    }

    private void updateDetailsFragment(Step step) {
        Log.i(TAG, "Displaying info about step: " + step.getShortDescription());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(step);

        // check if there is already a fragment present
        if (fragmentManager.findFragmentById(R.id.detailsFrameLayout) == null) {
            transaction.add(R.id.detailsFrameLayout, detailsFragment);
        } else {
            transaction.replace(R.id.detailsFrameLayout, detailsFragment);
        }
        transaction.commit();
    }

    private void setUpViewPager() {
        RecipeStepsActivityPageAdapter mPageAdapter = new RecipeStepsActivityPageAdapter(this,
                getSupportFragmentManager(), mRecipeId);
        Objects.requireNonNull(mViewPager).setAdapter(mPageAdapter);
        Objects.requireNonNull(mTabLayout).setupWithViewPager(mViewPager);
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onStepSelected(Step step) {
        if (mTwoPane) {
            // Following Master/Detail pattern
            Log.i(TAG, "In two pane mode and adding details fragment");
            Objects.requireNonNull(mIngredientsButton).setVisibility(View.VISIBLE);
            updateDetailsFragment(step);
        } else {
            // Since we are not on a Tablet create an intent to launch the RecipeDetailsActivity
            Log.i(TAG, "Not in two pane and launching RecipeDetailsActivity");
            Intent detailsIntent = new Intent(this, RecipeDetailsActivity.class);
            detailsIntent.putExtra("stepId", step.stepId);
            detailsIntent.putExtra("recipeId", mRecipeId);
            Log.i(TAG, "stepId = " + step.stepId);
            startActivity(detailsIntent);
        }
    }
}
