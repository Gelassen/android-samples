package com.coderbunker.TestJavaRx;

import com.coderbunker.TestJavaRx.di.TestApplication;
import com.coderbunker.TestJavaRx.di.TestComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(
        application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 21
)
public class BaseTest {

    TestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) App.getComponent();
    }
}