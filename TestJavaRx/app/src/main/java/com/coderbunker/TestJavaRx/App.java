package com.coderbunker.TestJavaRx;


import android.app.Application;

import com.coderbunker.TestJavaRx.di.AppComponent;
import com.coderbunker.TestJavaRx.di.DaggerAppComponent;
import com.coderbunker.TestJavaRx.di.GeneralModel;

public class App extends Application {

    public static final String TAG = "TAG";

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .generalModel(new GeneralModel(this))
                .build();
    }
}
