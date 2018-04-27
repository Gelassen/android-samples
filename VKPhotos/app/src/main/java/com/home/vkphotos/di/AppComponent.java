package com.home.vkphotos.di;

import com.home.vkphotos.photos.detailed.DetailedPagerAdapter;
import com.home.vkphotos.photos.preview.FilmsActivity;
import com.home.vkphotos.photos.preview.PhotoAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(FilmsActivity activity);

    void inject(PhotoAdapter photoAdapter);

    void inject(DetailedPagerAdapter detailedPagerAdapter);
}
