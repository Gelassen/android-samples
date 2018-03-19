package com.coderbunker.daggertest.integration;


import com.coderbunker.daggertest.BaseTest;
import com.coderbunker.daggertest.dto.IModel;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

public class OriginalTest extends BaseTest {

    @Inject
    IModel model;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        appComponent.inject(this);
    }

    @Test
    public void testSomething() throws Exception {


    }
}
