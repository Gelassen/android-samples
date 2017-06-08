package com.coderbunker.TestJavaRx.di;


import com.coderbunker.TestJavaRx.ListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GeneralModel.class})
public interface AppComponent {
    void inject(ListPresenter listPresenter);
}
