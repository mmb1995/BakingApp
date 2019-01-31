package com.example.android.bakebetter.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.model.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 2, exportSchema = false)
public abstract class RecipesDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DELETE FROM Recipes");
            database.execSQL("DELETE FROM Ingredients");
            database.execSQL("DELETE FROM Steps");
        }
    };


}
