package com.example.android.bakebetter.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.model.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class RecipesDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();

}
