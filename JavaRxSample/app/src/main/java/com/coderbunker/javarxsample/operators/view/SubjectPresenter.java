package com.coderbunker.javarxsample.operators.view;


import android.util.Log;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.model.Model;

import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subscriptions.CompositeSubscription;

public class SubjectPresenter implements ISubjectPresenter {

    private Model model;

    private CompositeSubscription subscription;

    public SubjectPresenter() {
        this.subscription = new CompositeSubscription();
        this.model = new Model();
    }

    @Override
    public void onDestroy() {
        subscription.clear();
    }

    @Override
    public void onAsyncSubject() {
        Log.d(App.TAG, "= = =");
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(String cityItem) {
                Log.d(App.TAG, "City: on next " + cityItem);
            }
        });
        subject.onNext("Emit first");
        subject.onNext("Emit second");
        subject.onNext("Emit third");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "[2] onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "[2] onError", e);
            }

            @Override
            public void onNext(String s) {
                Log.e(App.TAG, "[2] onNext: " + s);
            }
        });
        subject.onNext("Emit fourth");
    }

    @Override
    public void onBehaviorSubject() {
        Log.d(App.TAG, "= = =");
        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(String cityItem) {
                Log.d(App.TAG, "City: on next " + cityItem);
            }
        });
        subject.onNext("Emit first");
        subject.onNext("Emit second");
        subject.onNext("Emit third");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "[2] onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "[2] onError", e);
            }

            @Override
            public void onNext(String s) {
                Log.e(App.TAG, "[2] onNext: " + s);
            }
        });
        subject.onNext("Emit fourth");
    }

    @Override
    public void onPublishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(String cityItem) {
                Log.d(App.TAG, "City: on next " + cityItem);
            }
        });
        subject.onNext("Emit first");
        subject.onNext("Emit second");
        subject.onNext("Emit third");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "[2] onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "[2] onError", e);
            }

            @Override
            public void onNext(String s) {
                Log.e(App.TAG, "[2] onNext: " + s);
            }
        });
        subject.onNext("Emit fourth");
    }

    @Override
    public void onReplySubject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(String cityItem) {
                Log.d(App.TAG, "City: on next " + cityItem);
            }
        });
        subject.onNext("Emit first");
        subject.onNext("Emit second");
        subject.onNext("Emit third");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "[2] onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "[2] onError", e);
            }

            @Override
            public void onNext(String s) {
                Log.e(App.TAG, "[2] onNext: " + s);
            }
        });
        subject.onNext("Emit fourth");
    }

}
