package com.coderbunker.daggertest.integration;


import com.coderbunker.daggertest.di.TestComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {IntegrationModule.class})
public interface IntegrationComponent extends TestComponent {
    void inject(ModifiedTest modifiedTest);
}
