package com.coderbunker.appdagger.modules;


import com.coderbunker.appdagger.presenters.AppPresenter;
import com.coderbunker.appdagger.presenters.IPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    public IPresenter getPresenter() {
        return new AppPresenter();
    }

}
