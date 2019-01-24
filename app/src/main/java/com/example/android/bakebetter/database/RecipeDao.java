package com.example.android.bakebetter.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.model.Step;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void addAllRecipes(List<Recipe> recipes);

    @Insert
    void addAllSteps(List<Step> steps);

    @Insert
    void addAllIngredients(List<Ingredient> ingredients);

    @Query("SELECT * FROM RECIPES")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM STEPS WHERE recipeId=:recipeId")
    LiveData<List<Step>> getStepsForRecipe(final Long recipeId);

    @Query("SELECT * FROM INGREDIENTS WHERE recipeId=:recipeId")
    LiveData<List<Ingredient>> getIngredientsForRecipe(final Long recipeId);

    @Query("SELECT COUNT(*) FROM RECIPES")
    int hasRecipes();
}
