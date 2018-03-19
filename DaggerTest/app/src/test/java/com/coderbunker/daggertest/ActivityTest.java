package com.coderbunker.daggertest;


import android.os.Bundle;

import com.coderbunker.daggertest.presenter.FakePresenter;

import org.junit.*;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

public class ActivityTest extends BaseTest {

    private SampleActivity activity;

    @Inject
    FakePresenter presenter;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

//        DaggerTestViewComponent.builder().build().inject(this);

        activity = Robolectric.setupActivity(SampleActivity.class);

        // TODO implement logic
    }

    @Test
    public void testOnCreate() throws Exception {
        activity.onCreate(Bundle.EMPTY);

    }
}
