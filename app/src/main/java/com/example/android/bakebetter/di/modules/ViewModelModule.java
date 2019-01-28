package com.example.android.bakebetter.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.bakebetter.di.ViewModelKey;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.IngredientsViewModel;
import com.example.android.bakebetter.viewmodels.RecipeListViewModel;
import com.example.android.bakebetter.viewmodels.StepsViewModel;

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
    @IntoMap
    @ViewModelKey(IngredientsViewModel.class)
    abstract ViewModel bindIngredientsViewModel(IngredientsViewModel ingredientsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StepsViewModel.class)
    abstract ViewModel bindStepsListViewModel(StepsViewModel stepsViewModel);



    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
