package com.example.android.bakebetter.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.bakebetter.model.Photo;
import com.example.android.bakebetter.network.UnsplashApiService;
import com.example.android.bakebetter.utils.RecipeUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnsplashRepository {
    private static final String TAG = "UnsplashRepository";

    private final UnsplashApiService mUnsplashApiService;

    @Inject
    public UnsplashRepository(UnsplashApiService service) {
        this.mUnsplashApiService = service;
    }

    /**
     * Calls the Unsplash api to get a photo related to the given query
     * @param recipeName the name of a Recipe
     * @return
     */
    public LiveData<Photo> getPhotoByRecipeName(String recipeName) {
        final MutableLiveData<Photo> result = new MutableLiveData<>();

        mUnsplashApiService.getPhotoByRecipeName(recipeName, RecipeUtils.UNSPLASH_API_KEY).enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });

        return result;
    }
}
