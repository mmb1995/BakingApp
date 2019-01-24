package com.example.android.bakebetter.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.bakebetter.database.RecipeDao;
import com.example.android.bakebetter.database.RecipesDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    RecipesDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, RecipesDatabase.class,"recipesDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    RecipeDao provideRecipeDao(RecipesDatabase db) {
        return db.recipeDao();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
