package com.coderbunker.daggertest;


import android.app.Application;

import com.coderbunker.daggertest.di.AppComponent;
import com.coderbunker.daggertest.di.DaggerAppComponent;

public class AppApplication extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    public AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().build();
    }
}
