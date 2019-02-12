package com.example.android.bakebetter.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeDetailsViewModel extends ViewModel {
    private static final String TAG = "RecipeDetailsViewModel";

    private final RecipeRepository mRepo;
    private LiveData<Step> mStep;

    @Inject
    public RecipeDetailsViewModel(RecipeRepository repo) {
        this.mRepo = repo;
    }

    public void init(int stepId) {
        if (this.mStep == null) {
            mStep = mRepo.getStep(stepId);
        }
    }

    public LiveData<Step> getStep() {
        return mStep;
    }
}
