package com.coderbunker.javarxsample.citylist.view;


import android.util.Log;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.citylist.dto.CityItem;
import com.coderbunker.javarxsample.citylist.model.Model;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CityListPresenter implements IPresenter {

    private IView view;
    private Model model;

    // TODO what if we have to maintain several different observers?
    private CompositeSubscription subscription;

    public CityListPresenter(IView view) {
        this.view = view;
        model = new Model();
        subscription = new CompositeSubscription();
    }

    @Override
    public void triggerEvent() {
        Subscription modelSubscription = model.getModel().subscribe(new Observer<List<CityItem>>() {
            @Override
            public void onCompleted() {
                Log.d(App.TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(App.TAG, "Failed to obtain data", e);
                view.showError();
            }

            @Override
            public void onNext(List<CityItem> cityItems) {
                Log.d(App.TAG, "List items: " + cityItems.size());
                view.showList(cityItems);
            }
        });
        subscription.add(modelSubscription);
    }

    @Override
    public void onResume() {
        Log.d(App.TAG, "onResume");
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        Log.d(App.TAG, "onDestroy:Subscription: " + subscription.hasSubscriptions());
    }
}
