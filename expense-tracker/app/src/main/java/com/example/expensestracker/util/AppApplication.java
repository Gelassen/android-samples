package com.example.expensestracker.util;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApplication extends Application {

    private Api service;

    @Override
    public void onCreate() {
        super.onCreate();

        service = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/drive/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public Api getService() {
        return service;
    }


}
