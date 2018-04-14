package com.home.template;


import android.app.Application;

import com.home.template.di.AppComponent;
import com.home.template.di.DaggerAppComponent;
import com.home.template.di.SchedulersModule;


public class AppApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .schedulersModule(new SchedulersModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
