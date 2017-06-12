package com.coderbunker.daggertest.integration;


import com.coderbunker.daggertest.dto.IModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class IntegrationModule {

    @Provides
    @Singleton
    public IModel getModel() {
        return mock(IModel.class);
    }

}
