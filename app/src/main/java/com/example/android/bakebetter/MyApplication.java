package com.example.android.bakebetter;

import android.app.Activity;
import android.app.Application;

import com.example.android.bakebetter.di.components.AppComponent;
import com.example.android.bakebetter.di.components.DaggerAppComponent;
import com.example.android.bakebetter.di.modules.AppModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .build()
                .inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
