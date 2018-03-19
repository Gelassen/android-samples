package com.coderbunker.daggertest.di;

import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.dto.TestStub;
import com.coderbunker.daggertest.presenter.FakePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    @Singleton
    @Provides
    public FakePresenter getPresentor() {
        return getPresentor();
    }

    @Provides
    @Singleton
    public static IModel getModel() {
        return new TestStub();
    }
}
