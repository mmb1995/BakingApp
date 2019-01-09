package com.example.android.bakebetter.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel{
    private static final String TAG = "RecipeListViewModel";

    // Holds the list of recipes
    private LiveData<List<Recipe>> mRecipesList;

    private final RecipeRepository mRepo;

    public RecipeListViewModel() {
        this.mRepo = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (mRecipesList == null) {
            Log.i(TAG, "Getting recipes from repo");
            mRecipesList = mRepo.getRecipes();
        }
        return mRecipesList;
    }
}
