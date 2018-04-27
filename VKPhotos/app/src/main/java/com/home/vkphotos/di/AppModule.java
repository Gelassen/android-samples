package com.home.vkphotos.di;


import android.content.Context;

import com.home.vkphotos.utils.ImageFetcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public ImageFetcher providesImageFetcher() {
        return new ImageFetcher(context);
    }
}
