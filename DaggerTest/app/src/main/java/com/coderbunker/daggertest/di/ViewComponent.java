package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.SampleActivity;
import com.coderbunker.daggertest.presenter.FakePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewModule.class})
public interface ViewComponent {
    void inject(SampleActivity entity);
}
