package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.widget.RecipeWidgetService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract RecipeWidgetService contributeRecipeWidgetService();
}
