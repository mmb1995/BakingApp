package com.example.android.bakebetter.network;

import com.example.android.bakebetter.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * REST access point used by Retrofit
 */
public interface RecipeService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
