package com.example.android.bakebetter.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.bakebetter.model.Recipe;
import com.example.android.bakebetter.network.RecipeService;
import com.example.android.bakebetter.utils.RecipeUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private static final String TAG="RecipeRepository";

    private final RecipeService mWebService;

    private static RecipeRepository mInstance;

    private RecipeRepository(RecipeService service) {
        this.mWebService = service;
    }

    /**
     * Singleton constructor
     * @return
     */
    public static RecipeRepository getInstance() {
        if (mInstance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            // Create Retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RecipeUtils.RECIPE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            RecipeService service = retrofit.create(RecipeService.class);
            mInstance = new RecipeRepository(service);
        }
        return mInstance;
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
