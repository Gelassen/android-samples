package com.coderbunker.TestJavaRx;


import android.util.Log;

import com.coderbunker.TestJavaRx.model.IModel;
import com.coderbunker.TestJavaRx.stub.Stub;
import com.coderbunker.TestJavaRx.view.IView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

import static org.mockito.Mockito.mock;

public class JavaRxTest extends BaseTest {

    @Inject
    IModel model;

    ListPresenter presenter;

    IView view;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        view = mock(IView.class);
        presenter = new ListPresenter(view);

        Mockito.doAnswer(invocation -> Observable.from(Stub.getData()).toList())
                .when(model)
                .getItems();
    }

    @Test
    public void testJavaRx() throws Exception {
        // test data
        model.getItems().subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(App.TAG, "onError", e);
            }

            @Override
            public void onNext(List<String> strings) {
                Log.d(App.TAG, "onNext: " + strings.size());
            }
        });
    }

    @Test
    public void testJavaRxWithEmptyData() throws Exception {
        Mockito.doAnswer(invocation -> Observable.just(null))
                .when(model)
                .getItems();

        model.getItems().subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(App.TAG, "onError", e);
            }

            @Override
            public void onNext(List<String> strings) {
                Log.d(App.TAG, "onNext: " + (strings == null));
            }
        });
    }

    @Test
    public void testListPresenter() throws Exception {
        Mockito.doAnswer(invocation -> Observable.just(null))
                .when(model)
                .getItems();

        presenter.processData();

        Mockito.verify(view).showEmptyList();
    }

    @Test
    public void testDefaultScenario() throws Exception {
        presenter.processData();

        Mockito.verify(view).showList(Stub.getData());

    }
}
