package com.example.android.bakebetter.di.components;

import android.app.Application;

import com.example.android.bakebetter.MyApplication;
import com.example.android.bakebetter.di.modules.ActivityModule;
import com.example.android.bakebetter.di.modules.AppModule;
import com.example.android.bakebetter.di.modules.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class
})

public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
