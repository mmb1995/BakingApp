package com.example.android.bakebetter.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.bakebetter.di.ViewModelKey;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.RecipeListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel.class)
    abstract ViewModel bindRecipeListViewModel(RecipeListViewModel recipeListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
