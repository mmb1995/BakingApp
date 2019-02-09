package com.example.android.bakebetter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PreferenceUtil {
    private static final String RECIPE_ID = "recipe_id";
    private static final Long RECIPE_ID_DEFAULT = -1L;

    public static final Long getCurrentRecipeId(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(RECIPE_ID, RECIPE_ID_DEFAULT);
    }

    public static void setCurrentRecipe(@NonNull Context context, Long recipeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(RECIPE_ID, recipeId);
        editor.apply();
    }
}
