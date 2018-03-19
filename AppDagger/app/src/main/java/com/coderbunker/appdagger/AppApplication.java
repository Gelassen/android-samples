package com.coderbunker.appdagger;


import android.app.Application;

import com.coderbunker.appdagger.modules.AppComponent;
import com.coderbunker.appdagger.modules.DaggerAppComponent;
import com.coderbunker.appdagger.modules.PresenterModule;

public class AppApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // implement injections binding
        appComponent = DaggerAppComponent
                .builder()
                .presenterModule(new PresenterModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
