package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.network.RecipeService;
import com.example.android.bakebetter.utils.RecipeUtils;

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
    Retrofit provideRecipesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(RecipeUtils.RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService(Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }

}
