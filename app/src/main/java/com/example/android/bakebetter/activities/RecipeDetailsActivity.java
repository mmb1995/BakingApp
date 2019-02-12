package com.example.android.bakebetter.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.StepsPageAdapter;
import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.StepsViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeDetailsActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "RecipeDetailsActivity";
    private static final String ARG_STEP_ID = "stepId";
    private static final String ARG_RECIPE_ID = "recipeId";
    private int mCurrentStepIndex;
    private List<Step> mSteps;

    @BindView(R.id.fragmentDetailsViewPager)
    ViewPager mPager;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    FactoryViewModel mFactoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        getSteps();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void getSteps() {
        Long recipeId = getIntent().getLongExtra(ARG_RECIPE_ID, 0);
        StepsViewModel model = ViewModelProviders.of(this, mFactoryViewModel)
                .get(StepsViewModel.class);
        model.init(recipeId);
        model.getSteps().observe(this, steps -> {
            if (steps != null) {
                this.mSteps = steps;
                // Tells User how to navigate through the different steps for the recipe
                Toast.makeText(this,getString(R.string.toast_swipe_message),
                        Toast.LENGTH_SHORT).show();
                setupViewPager();
            } else {
                Toast.makeText(this, getString(R.string.toast_error_message),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setupViewPager() {
        StepsPageAdapter adapter = new StepsPageAdapter(getSupportFragmentManager(), mSteps);
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                // Release assets associated with the fragment that is being replaced
                RecipeDetailsFragment prevFragment = adapter.getCachedFragment(mCurrentStepIndex);
                if (prevFragment != null ) {
                    prevFragment.loseFocus();
                }

                // Give focus to incoming fragment
                mCurrentStepIndex = position;
                RecipeDetailsFragment nextFragment = adapter.getCachedFragment(mCurrentStepIndex);
                if (nextFragment != null) {
                    nextFragment.gainFocus();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mCurrentStepIndex = getCurrentStepIndex();
        mPager.setCurrentItem(mCurrentStepIndex);
    }

    private int getCurrentStepIndex() {
        int mStepId = getIntent().getIntExtra(ARG_STEP_ID, 0);
        for (int i = 0; i < mSteps.size(); i++) {
            if (mSteps.get(i).stepId == mStepId) {
                return i;
            }
        }
        return 0;
    }
}
