package com.example.android.bakebetter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeStepsActivityPageAdapter;
import com.example.android.bakebetter.fragments.RecipeStepListFragment;
import com.example.android.bakebetter.model.Step;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeStepActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        RecipeStepListFragment.OnStepSelectedListener {
    private static final String TAG = "RecipeStepActivity";

    public static final String ARG_RECIPE_ID = "recipe";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(R.id.recipe_details_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.recipe_viewpager)
    ViewPager mViewPager;

    private Long mRecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        // Get the data passed in from the starting intent
        this.mRecipeId = getIntent().getLongExtra(ARG_RECIPE_ID, 0);

        if (this.mRecipeId != null) {
            setUpViewPager();
        }
    }

    private void setUpViewPager() {
        RecipeStepsActivityPageAdapter mPageAdapter = new RecipeStepsActivityPageAdapter(this,
                getSupportFragmentManager(), mRecipeId);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onStepSelected(Step step) {
        Intent detailsIntent = new Intent(this, RecipeDetailsActivity.class);
        detailsIntent.putExtra("step", step);
        startActivity(detailsIntent);
    }
}
