package com.coderbunker.TestJavaRx;


import android.util.Log;

import com.coderbunker.TestJavaRx.model.IModel;
import com.coderbunker.TestJavaRx.view.IView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class ListPresenter {

    @Inject
    IModel model;

    IView view;

    public ListPresenter(IView view) {
        App.getComponent().inject(this);
        this.view = view;
    }

    public void processData() {
        model.getItems().subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(List<String> strings) {
                boolean isShowList = strings != null && strings.size() > 0;
                if (isShowList) {
                    view.showList(strings);
                } else {
                    view.showEmptyList();
                }
            }
        });
    }
}
