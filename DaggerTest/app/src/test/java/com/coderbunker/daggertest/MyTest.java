package com.coderbunker.daggertest;


import com.coderbunker.daggertest.di.TestModule;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class MyTest extends BaseTest {

    @Inject
    TestModule testModule;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        appComponent.inject(this);
    }

    @Test
    public void testSumOfTwoNumbers() throws Exception {
        assertEquals(4, 2+2);
    }
}
