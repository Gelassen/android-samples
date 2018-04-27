package com.home.vkphotos;


import android.app.Application;

import com.home.vkphotos.di.AppComponent;
import com.home.vkphotos.di.AppModule;
import com.home.vkphotos.di.DaggerAppComponent;
import com.vk.sdk.VKSdk;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }

    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
