package com.example.android.bakebetter.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.bakebetter.database.RecipeDao;
import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.network.RecipeService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RecipeRepository {
    private static final String TAG="RecipeRepository";

    private final RecipeService mWebService;
    private final RecipeDao mRecipeDao;
    private final Executor mExecutor;

    /**
     * Singleton constructor arguments provided by dagger injection
     * @return
     */
    @Inject
    public RecipeRepository(RecipeService service, RecipeDao recipeDao, Executor executor) {
        this.mWebService = service;
        this.mRecipeDao = recipeDao;
        this.mExecutor = executor;
    }

    public LiveData<List<Recipe>> getRecipes() {
       refreshRecipes();
       return mRecipeDao.getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredientsByRecipe(Long recipeId) {
        refreshRecipes();
        return mRecipeDao.getIngredientsForRecipe(recipeId);
    }

    public LiveData<List<Step>> getStepsByRecipe(Long recipeId) {
        refreshRecipes();
        return mRecipeDao.getStepsForRecipe(recipeId);
    }

    public LiveData<Step> getStep(int stepId) {
        return mRecipeDao.getStepById(stepId);
    }

    /**
     * This should only be called by the widget to avoid blocking the main thread
     * @param recipeId
     * @return
     */
    public List<Ingredient> getIngredientsSync(Long recipeId) {
        return mRecipeDao.getIngredientsSync(recipeId);
    }

    /**
     * NOTE VERY DANGEROUS ONLY FOR DEBUGGING REMOVE LATER
     */
    public void nukeTable() {
        mExecutor.execute(() -> {
            mRecipeDao.nukeRecipes();
            mRecipeDao.nukeIngredients();
            mRecipeDao.nukeSteps();
        });
    }

    /**
     * REMOVE LATER
     */
    public void checkRecords() {
        mExecutor.execute(() -> {
            int count = mRecipeDao.hasRecipes();
            Log.i(TAG, "records = " + count);
        });
    }

    /**
     * Checks to see if the database is empty, and if so performs a network request and adds the
     * results to the database
     */
    private void refreshRecipes() {
        // Works on background thread
        mExecutor.execute(() -> {
            // Checks if there are recipes present in the database
            int count = mRecipeDao.hasRecipes();
            if (count == 0) {
                mWebService.getRecipes().enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                        Log.i(TAG, Objects.requireNonNull(response.body()).toString());
                        if (response.isSuccessful()) {
                            insertRecipes(response.body());
                            insertIngredients(response.body());
                            insertSteps(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                        // TODO

                        t.printStackTrace();
                    }
                });
            }
        });
    }

    /**
     * Connects all of the Ingredients to the recipe they belong to by supplying them with a
     * foreign key to represent the recipe they are associated with
     * @param ingredients
     * @param recipeId
     */
    private void addForeignKeyToIngredients(final List<Ingredient> ingredients, Long recipeId) {
        for (Ingredient ingredient : ingredients) {
            ingredient.recipeId = recipeId;
        }
    }

    private void addForeignKeyToSteps(final List<Step> steps, Long recipeId) {
        for (Step step: steps) {
            step.recipeId = recipeId;
        }
    }

    private void insertIngredients(final List<Recipe> recipes) {
        for (Recipe recipe: recipes) {
            final List<Ingredient> ingredients = recipe.getIngredients();
            addForeignKeyToIngredients(ingredients, recipe.getId());
            mExecutor.execute(() -> mRecipeDao.addAllIngredients(ingredients));
        }
    }

    private void insertSteps(final List<Recipe> recipes) {
        for (Recipe recipe: recipes) {
            final List<Step> steps = recipe.getSteps();
            addForeignKeyToSteps(steps, recipe.getId());
            mExecutor.execute(() -> mRecipeDao.addAllSteps(steps));
        }
    }

    private void insertRecipes(final List<Recipe> recipes) {
        mExecutor.execute(() -> mRecipeDao.addAllRecipes(recipes));
    }

}
