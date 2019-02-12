package com.example.android.bakebetter.di.components;

import com.example.android.bakebetter.MyApplication;
import com.example.android.bakebetter.di.modules.ActivityModule;
import com.example.android.bakebetter.di.modules.AppModule;
import com.example.android.bakebetter.di.modules.FragmentModule;
import com.example.android.bakebetter.di.modules.ServiceModule;
import com.example.android.bakebetter.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ServiceModule.class,
        FragmentModule.class,
        ViewModelModule.class
})

public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
