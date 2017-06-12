package com.coderbunker.daggertest;


import android.util.Log;

import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.dto.TestData;
import com.coderbunker.daggertest.model.Model;
import com.coderbunker.daggertest.presenter.ListPresenter;
import com.coderbunker.daggertest.view.IView;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observer;
import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ListTest extends BaseTest {

    ListPresenter presenter;

//    @Inject
    IModel model;

    IView view;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        appComponent.inject(this);

        view = mock(IView.class);
        presenter = new ListPresenter(view);
        model = mock(Model.class);
    }

    @Test
    public void testEmittedList() throws Exception {
        model.getData().subscribe(new Observer<List<TestData>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to emit the list", e);
            }

            @Override
            public void onNext(List<TestData> testDatas) {
                Log.d(App.TAG, "Failed to obtain data: " + testDatas.size());
                assertEquals(7, testDatas.size());
            }
        });

    }

    @Test
    public void testModel() throws Exception {
        doAnswer(invocation -> Observable.from(com.coderbunker.daggertest.Test.generateStubData()).toList())
                .when(model)
                .getData();

        model.getData().subscribe(new Observer<List<TestData>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(List<TestData> testDatas) {
                assertEquals(70, testDatas.size());
            }
        });
    }

    @Test
    public void testListPresenter() throws Exception {
        doAnswer(invocation -> rx.Observable.from(com.coderbunker.daggertest.Test.generateStubData()).toList())
                .when(model)
                .getData();

        presenter.processData();

        verify(view).showList(com.coderbunker.daggertest.Test.generateStubData());
    }
}
