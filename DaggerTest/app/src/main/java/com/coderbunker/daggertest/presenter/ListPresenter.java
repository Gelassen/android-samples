package com.coderbunker.daggertest.presenter;


import android.util.Log;

import com.coderbunker.daggertest.App;
import com.coderbunker.daggertest.AppApplication;
import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.dto.TestData;
import com.coderbunker.daggertest.view.IView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class ListPresenter {
    private IView view;

    @Inject
    IModel model;

    public ListPresenter(IView view) {
        this.view = view;
        AppApplication.getAppComponent().inject(this);
    }

    public void showList() {
        view.showList();
    }

    public void processData() {
        Observable<List<TestData>> data = model.getData();
        data.subscribe(new Observer<List<TestData>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(App.TAG, "onError", e);
            }

            @Override
            public void onNext(List<TestData> testData) {
                boolean isThereIsSomethingToShow = testData.size() != 0;
                if (isThereIsSomethingToShow) {
                    view.showList(testData);
                } else {
                    view.showEmptyList();
                }
            }
        });
    }
}
