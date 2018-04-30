package com.home.vkphotos.di;


import android.content.Context;

import com.home.vkphotos.LifeCycleListener;
import com.home.vkphotos.utils.ImageFetcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public ImageFetcher providesImageFetcher(LifeCycleListener lifeCycleListener) {
        return new ImageFetcher(context, lifeCycleListener);
    }

    @Singleton
    @Provides
    public LifeCycleListener provideLifeCycleListener() {
        return new LifeCycleListener();
    }
}
