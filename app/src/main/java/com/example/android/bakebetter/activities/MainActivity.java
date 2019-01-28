package com.example.android.bakebetter.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeGalleryAdapter;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.RecipeListViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MainActivity extends AppCompatActivity implements RecipeGalleryAdapter.OnRecipeClickListener,
        HasActivityInjector {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    FactoryViewModel mFactoryViewModel;

    private RecipeGalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        this.mAdapter = new RecipeGalleryAdapter(this, this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.setAdapter(mAdapter);
        configureViewModel();
    }

    /**
     * Launches the steps activity to display the ingredients and steps needed to make the recipe
     * selected by the user
     * @param position position of the selected recipe in the adapter
     */
    @Override
    public void onRecipeClicked(int position) {
        Recipe currentRecipe = mAdapter.getRecipeAtPosition(position);
        Log.i(TAG, "recipe =" + currentRecipe.getName());
        Intent startStepsActivityIntent = new Intent(MainActivity.this, RecipeStepActivity.class);
        startStepsActivityIntent.putExtra(RecipeStepActivity.ARG_RECIPE_ID, currentRecipe.getId());
        startActivity(startStepsActivityIntent);
    }

    private void configureViewModel() {
        Log.i("TAG", "Setting up ViewModel");
        RecipeListViewModel model = ViewModelProviders.of(this, mFactoryViewModel).get(RecipeListViewModel.class);

        // Set up the Observer
        model.getRecipes().observe(this, recipes -> {
            if (recipes != null) {
                Log.i(TAG, "Received recipes");
                mProgressBar.setVisibility(View.GONE);
                mAdapter.setRecipesList(recipes);
            }
        });
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
