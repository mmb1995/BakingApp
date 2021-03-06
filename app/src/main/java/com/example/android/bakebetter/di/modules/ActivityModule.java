package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.activities.MainActivity;
import com.example.android.bakebetter.activities.RecipeDetailsActivity;
import com.example.android.bakebetter.activities.RecipeMasterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract RecipeMasterActivity contributeRecipeStepActivity();

    @ContributesAndroidInjector
    abstract RecipeDetailsActivity contributesRecipeDetailsActivity();
}
