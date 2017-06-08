package com.coderbunker.daggertest.di;

import android.app.Activity;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(Activity activity);
}
