package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.database.RecipeDao;
import com.example.android.bakebetter.network.RecipeService;
import com.example.android.bakebetter.repository.RecipeRepository;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    RecipeRepository providesRecipeRepository(RecipeService service, RecipeDao recipeDao, Executor executor) {
        return new RecipeRepository(service, recipeDao, executor);
    }
}
