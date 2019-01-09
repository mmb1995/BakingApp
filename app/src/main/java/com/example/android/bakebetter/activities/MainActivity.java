package com.example.android.bakebetter.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeGalleryAdapter;
import com.example.android.bakebetter.viewmodels.RecipeListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeGalleryAdapter.OnRecipeClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;

    private RecipeGalleryAdapter mAdapter;
    private RecipeListViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        this.mAdapter = new RecipeGalleryAdapter(this);
        this.mRecyclerView.setLayoutManager(mGridLayoutManager);
        this.mRecyclerView.setAdapter(mAdapter);
        configureViewModel();
    }

    @Override
    public void onRecipeClicked(int position) {
        Toast.makeText(this, "Recipe Clicked", Toast.LENGTH_SHORT).show();
    }

    private void configureViewModel() {
        Log.i("TAG", "Setting up ViewModel");
        RecipeListViewModel model = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        // Set up the Observer
        model.getRecipes().observe(this, recipes -> {
            if (recipes != null) {
                mProgressBar.setVisibility(View.GONE);
                mAdapter.setRecipesList(recipes);
            }
        });
    }
}
