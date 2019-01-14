package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.network.RecipeService;
import com.example.android.bakebetter.network.UnsplashApiService;
import com.example.android.bakebetter.utils.RecipeUtils;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworksModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return  builder.build();
    }

    @Provides
    @Singleton
    @Named("RecipesRetrofit")
    Retrofit provideRecipesRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeUtils.RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    @Named("UnsplashRetrofit")
    Retrofit provideUnsplashRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeUtils.UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService(@Named("RecipesRetrofit") Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }

    @Provides
    @Singleton
    UnsplashApiService provideUnsplashApiService(@Named("UnsplashRetrofit") Retrofit retrofit) {
        return retrofit.create(UnsplashApiService.class);
    }
}
