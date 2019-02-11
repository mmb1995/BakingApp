package com.example.android.bakebetter.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeGalleryAdapter;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.utils.PreferenceUtil;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.RecipeListViewModel;
import com.example.android.bakebetter.widget.WidgetUpdateService;

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

        // Check phone configuration
        if (getResources().getBoolean(R.bool.isTablet)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
                this.mRecyclerView.setLayoutManager(layoutManager);
            } else {
                GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
                this.mRecyclerView.setLayoutManager(layoutManager);
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            this.mRecyclerView.setLayoutManager(layoutManager);
        } else {
            // Not a tablet and phone is in landscape orientation
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            this.mRecyclerView.setLayoutManager(layoutManager);
        }
        this.mAdapter = new RecipeGalleryAdapter(this, this);
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
        updateSharedPreferencesAndWidget(currentRecipe);

        // Create Intent to launch RecipeMasterActivity
        Intent startStepsActivityIntent = new Intent(MainActivity.this, RecipeMasterActivity.class);
        startStepsActivityIntent.putExtra(RecipeMasterActivity.ARG_RECIPE_ID, currentRecipe.getId());
        startActivity(startStepsActivityIntent);
    }

    private void configureViewModel() {
        Log.i("TAG", "Setting up ViewModel");
        RecipeListViewModel model = ViewModelProviders.of(this, mFactoryViewModel).get(RecipeListViewModel.class);

        // Set up the Observer
        model.getRecipes().observe(this, recipes -> {
            mProgressBar.setVisibility(View.GONE);
            if (recipes != null) {
                Log.i(TAG, "Received recipes");
                mAdapter.setRecipesList(recipes);
            } else {
                // There was an error loading the recipes
                Toast.makeText(this, getString(R.string.toast_error_message),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateSharedPreferencesAndWidget(Recipe recipe) {
        // Update shared Preferences
        PreferenceUtil.setCurrentRecipeId(this, recipe.getId());
        PreferenceUtil.setCurrentRecipeName(this, recipe.getName());

        // Start service to update widget to show ingredients for the newly selected recipe
        Log.i(TAG, "updating widget");
        WidgetUpdateService.startWidgetUpdate(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
