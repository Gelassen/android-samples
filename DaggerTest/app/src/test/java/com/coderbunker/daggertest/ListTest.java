package com.coderbunker.daggertest;


import com.coderbunker.daggertest.presenter.ListPresenter;

import org.junit.Before;
import org.junit.Test;

public class ListTest extends BaseTest {

    ListPresenter presenter;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        appComponent.inject(this);
    }

    @Test
    public void testListPresenter() throws Exception {
        presenter.showList();
    }
}
