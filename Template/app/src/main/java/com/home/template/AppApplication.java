package com.home.template;


import android.app.Application;



public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        DaggerAppComponent.builder()
//                .schedulersModule(new SchedulersModule(this))
//                .build();
    }
}
