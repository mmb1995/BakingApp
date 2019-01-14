package com.example.android.bakebetter.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.example.android.bakebetter.repository.UnsplashRepository;

public class UnsplashViewModel extends ViewModel {

    private UnsplashRepository mRepo;


    public UnsplashViewModel(UnsplashRepository repository) {
        this.mRepo = repository;
    }
}
