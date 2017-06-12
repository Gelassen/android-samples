package com.coderbunker.appdagger.modules;

import com.coderbunker.appdagger.MainActivity;

import dagger.Component;

@Component(modules= {PresenterModule.class} )
public interface AppComponent {
    void inject(MainActivity activity);
}
