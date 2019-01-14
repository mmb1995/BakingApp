package com.example.android.bakebetter.network;

import com.example.android.bakebetter.model.Photo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashApiService {

    @GET("photos/random")
    Call<Photo> getPhotoByRecipeName(@Query("query") String query,
                                     @Query("client_id") String apiKey);

}
