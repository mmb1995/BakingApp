package com.example.android.bakebetter.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.network.RecipeService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private static final String TAG="RecipeRepository";

    private final RecipeService mWebService;


    /**
     * Singleton constructor
     * @return
     */
    @Inject
    public RecipeRepository(RecipeService service) {
        this.mWebService = service;
    }

    public LiveData<List<Recipe>> getRecipes() {
        final MutableLiveData<List<Recipe>> results = new MutableLiveData<>();
        Log.i(TAG,"Getting recipes");
        // Performs the network request
        mWebService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i(TAG,response.body().toString());
                if (response.isSuccessful()) {
                    results.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // TODO

                t.printStackTrace();
            }
        });
        return results;
    }

}
