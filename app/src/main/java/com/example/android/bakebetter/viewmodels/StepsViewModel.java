package com.example.android.bakebetter.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class StepsViewModel extends ViewModel {
    private static final String TAG = "StepsViewModel";

    private LiveData<List<Step>> steps;
    private RecipeRepository mRepository;

    @Inject
    public StepsViewModel(RecipeRepository repository) {
        this.mRepository = repository;
    }

    public void init(Long recipeId) {
        if (this.steps == null) {
            Log.i(TAG, "Getting steps from repo");
            steps = mRepository.getStepsByRecipe(recipeId);
        }
    }

    public LiveData<List<Step>> getSteps() {
        return steps;
    }
}


