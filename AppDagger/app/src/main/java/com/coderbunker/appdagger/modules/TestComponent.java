package com.coderbunker.appdagger.modules;


import com.coderbunker.appdagger.MainActivity;

import dagger.Component;

@Component(modules= {PresenterModule.class} )
public interface TestComponent {
    void inject(MainActivity activity);
}
