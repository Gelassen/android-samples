package com.coderbunker.daggertest;

import com.coderbunker.daggertest.BuildConfig;
import com.coderbunker.daggertest.di.TestApplication;
import com.coderbunker.daggertest.di.TestComponent;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(
        application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 21
)
public class BaseTest {
    protected TestComponent appComponent;

    @Before
    public void setUp() throws Exception {
        appComponent = (TestComponent) TestApplication.getAppComponent();
    }
}
