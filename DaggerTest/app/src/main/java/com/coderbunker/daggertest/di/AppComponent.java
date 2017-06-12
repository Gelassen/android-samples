package com.coderbunker.daggertest.di;

import android.app.Activity;

import com.coderbunker.daggertest.presenter.FakePresenter;
import com.coderbunker.daggertest.presenter.ListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(Activity activity);
    void inject(ListPresenter listPresenter);

    void inject(FakePresenter entity);
}
