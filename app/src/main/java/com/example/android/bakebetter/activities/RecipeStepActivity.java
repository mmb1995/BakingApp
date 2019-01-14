package com.example.android.bakebetter.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeStepsActivityPageAdapter;
import com.example.android.bakebetter.model.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeStepActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "RecipeStepActivity";

    public static final String ARG_RECIPE = "recipe";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(R.id.recipe_details_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.recipe_viewpager)
    ViewPager mViewPager;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        // Get the data passed in from the starting intent
        this.mRecipe = (Recipe) getIntent().getParcelableExtra(ARG_RECIPE);


        if (this.mRecipe != null) {
            Log.i(TAG, this.mRecipe.getName());
            setUpViewPager();
        }
    }

    private void setUpViewPager() {
        RecipeStepsActivityPageAdapter mPageAdapter = new RecipeStepsActivityPageAdapter(this,
                getSupportFragmentManager(), mRecipe.getSteps(), mRecipe.getIngredients());
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
