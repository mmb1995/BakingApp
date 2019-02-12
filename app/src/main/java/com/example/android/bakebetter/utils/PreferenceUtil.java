package com.example.android.bakebetter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PreferenceUtil {
    private static final String RECIPE_ID = "recipe_id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final Long RECIPE_ID_DEFAULT = -1L;
    private static final String RECIPE_NAME_DEFAULT ="Ingredients";

    public static Long getCurrentRecipeId(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(RECIPE_ID, RECIPE_ID_DEFAULT);
    }

    public static void setCurrentRecipeId(@NonNull Context context, Long recipeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(RECIPE_ID, recipeId);
        editor.apply();
    }

    public static String getCurrentRecipeName(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(RECIPE_NAME, RECIPE_NAME_DEFAULT);
    }

    public static void setCurrentRecipeName(@NonNull Context context, String recipeName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RECIPE_NAME, recipeName);
        editor.apply();
    }

}
