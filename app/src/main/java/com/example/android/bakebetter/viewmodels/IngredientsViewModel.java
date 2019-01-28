package com.example.android.bakebetter.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class IngredientsViewModel extends ViewModel {
    private static final String TAG = "IngredientsViewModel";

    private LiveData<List<Ingredient>> ingredients;
    private RecipeRepository mRepository;

    @Inject
    public IngredientsViewModel(RecipeRepository repository) {
        this.mRepository = repository;
    }

    public void init(Long recipeId) {
        if (this.ingredients == null) {
            Log.i(TAG, "Getting ingredients from repo");
            ingredients = mRepository.getIngredientsByRecipe(recipeId);
        }
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }
}
