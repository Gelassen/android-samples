package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.presenter.FakePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestViewModule {

    @Singleton
    @Provides
    public IModel getModel() {
        return mock(IModel.class);
    }

    @Singleton
    @Provides
    public FakePresenter getPresenter() {
        return mock(FakePresenter.class);
    }

}
