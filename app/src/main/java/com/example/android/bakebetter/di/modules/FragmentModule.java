package com.example.android.bakebetter.di.modules;

import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.fragments.RecipeIngredientsFragment;
import com.example.android.bakebetter.fragments.RecipeStepListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract RecipeStepListFragment contributeRecipeStepListFragment();

    @ContributesAndroidInjector
    abstract RecipeIngredientsFragment contributesRecipeIngredientFragment();

    @ContributesAndroidInjector
    abstract RecipeDetailsFragment contributesRecipeDetailsFragment();
}
