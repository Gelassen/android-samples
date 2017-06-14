package com.coderbunker.javarxsample.operators.view;


import android.util.Log;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.dto.AirCompaniesItem;
import com.coderbunker.javarxsample.dto.CityItem;
import com.coderbunker.javarxsample.dto.ICommon;
import com.coderbunker.javarxsample.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.coderbunker.javarxsample.R.id.list;

public class OperatorsPresenter implements IPresenter {

    private IView view;
    private Model model;

    private CompositeSubscription subscriptions;

    public OperatorsPresenter(IView view) {
        this.view = view;
        this.model = new Model();

        subscriptions = new CompositeSubscription();
    }

    /**
     * Sometimes we want to refine specific event only to be emitted by observable.
     * Let’s say in our above example we only want to emit only odd numbers out of the observable.
     * We can achieve this thing using another operator called filter()
     * */
    @Override
    public void runFilter() {
        Subscription subscription = model
                .getModel()
                .flatMap(list -> Observable.from(list))
                .filter(new Func1<CityItem, Boolean>() {
                    @Override
                    public Boolean call(CityItem cityItem) {
                        return cityItem.getCity().toLowerCase().contains("m");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CityItem>>() {
                    @Override
                    public void call(List<CityItem> cityItems) {
                        String toShow = buildResult(cityItems);
                        view.showResult(toShow);
                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void runMap() {
        Subscription subscription = model.getModel()
                .map(new Func1<List<CityItem>, HashMap<String, CityItem>>() {
                    @Override
                    public HashMap<String, CityItem> call(List<CityItem> cityItems) {
                        HashMap<String, CityItem> result = new HashMap<String, CityItem>();
                        for (CityItem cityItem : cityItems) {
                            result.put(cityItem.getCity(), cityItem);
                        }
                        return result;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HashMap<String, CityItem>>() {
                    @Override
                    public void call(HashMap<String, CityItem> stringCityItemHashMap) {
                        String data = buildResult(stringCityItemHashMap);
                        view.showResult(data);
                    }
                });
        subscriptions.add(subscription);
    }

    /**
     * skip(n) will suppress the first n items emitted by an Observable and emits data after n elements.
     * So, skip(2) will emit first 2 elements and starts from emitting the 3rd element
     *
     * take() is counter to the skip() operator. take(n) will emit only first n data elements
     * and ignores all data elements after n elements emitted.
     * */
    @Override
    public void takeNextItems(int skipAmount, int takeAmount) {
        Subscription subscription = model
                .getModel()
                .flatMap(s -> Observable.from(s))
                .skip(skipAmount)
                .take(takeAmount)
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CityItem>>() {
                    @Override
                    public void call(List<CityItem> list) {
                        view.showResult(buildResult(list));
                    }
                });
        subscriptions.add(subscription);
    }

    /**
     * As the name suggest, you can use concat() operator to concat two different observable
     * and emit the data stream for both the operators one after another.
     * */
    @Override
    public void runConcat() {
        Subscription subscription = Observable.concat(model.getModel(), model.getAirCompanies())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<? extends Object>>() {
                    @Override
                    public void call(List<? extends Object> objects) {
                        Log.d(App.TAG, "Returned type: " + objects.toString());
                    }
                });
        subscriptions.add(subscription);
    }

    /**
     * merge() operator works same as the concat() operator and combines data stream from two different observables.
     * The only difference between merge() and concat() operator is merge can interleave the outputs,
     * while concat will first wait for earlier streams to finish before processing later streams.
     * */
    @Override
    public void runMerge() {
        Log.d(App.TAG, "runMerge");
        Subscription subscription = Observable.merge(model.getAirCompanies(), model.getModel())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<? extends ICommon>>() {
                    @Override
                    public void call(List<? extends ICommon> result) {
                        Log.d(App.TAG, "List of result: " + result.toString());
                        Log.d(App.TAG, "List of result (size): " + result.size());
                    }
                });
        subscriptions.add(subscription);
    }


    /**
     * zip() operator combine the emissions of multiple Observables together via a specified function
     * and emit single items for each combination based on the results of this function.
     * */
    @Override
    public void runZip() {
        Subscription subscription = Observable.zip(
                model.getModel(),
                model.getAirCompanies(),
                model.getAirCompanies(),
                new Func3<List<CityItem>, List<AirCompaniesItem>, List<AirCompaniesItem>, List<ICommon>>() {
                    @Override
                    public List<ICommon> call(List<CityItem> t1, List<AirCompaniesItem> t2, List<AirCompaniesItem> t3) {
                        List<ICommon> result = new ArrayList<>(t1.size() + t2.size());
                        result.addAll(t1);
                        result.addAll(t2);
                        return result;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ICommon>>() {
                    @Override
                    public void call(List<ICommon> iCommons) {
                        view.showResult(buildResultForZip(iCommons));
                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void runDataFromCache() {
        Subscription subscription = model.getModel().cache(2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CityItem>>() {
                    @Override
                    public void call(List<CityItem> cityItems) {
                        view.showResult(buildResult(cityItems));
                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void runDataWithException() {
        model.getModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<List<CityItem>, List<CityItem>>() {
                    @Override
                    public List<CityItem> call(List<CityItem> cityItems) {
                        Log.d(App.TAG, "Items: " + cityItems.size());
                        if (cityItems.size() != 0) {
                            throw new IllegalStateException("This exception should be handled");
                        } else {
                            return cityItems;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CityItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(App.TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(App.TAG, "onCompleted", e);
                    }

                    @Override
                    public void onNext(List<CityItem> cityItems) {
                        Log.d(App.TAG, "onNext:size: " + cityItems.size());
                    }
                });
    }

    @Override
    public void runWithExceptionPropagate() {
        model.getModel().map(new Func1<List<CityItem>, List<CityItem>>() {
            @Override
            public List<CityItem> call(List<CityItem> cityItems) {
                try {
                    throw new ExecutionException("Execution exception", new Throwable());
                } catch (ExecutionException ex) {
                    Log.e(App.TAG, "Failed to run operation", ex);
                    throw Exceptions.propagate(ex);
                }
            }
        })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CityItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(App.TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(App.TAG, "onError", e);
                    }

                    @Override
                    public void onNext(List<CityItem> cityItems) {
                        Log.d(App.TAG, "onNext: " + cityItems.size());
                    }
                })
        ;
    }

    @Override
    public void runWithExceptionObservable() {
        final boolean isErrorEmulated = false;
        Subscription subscription = model.getModel()
                .flatMap(new Func1<List<CityItem>, Observable<?>>() {
                    @Override
                    public Observable<List<CityItem>> call(List<CityItem> cityItems) {
                        if (isErrorEmulated) {
                            return Observable.error(new Throwable("throwable"));
                        } else {
                            return Observable.just(cityItems);
                        }
                    }
                })
                .toList()
                .flatMap(new Func1<List<Object>, Observable<CityItem>>() {
                    @Override
                    public Observable<CityItem> call(List<Object> o) {
                        List<? extends Object> list = o;
                        List<CityItem> items = (List<CityItem>) list;
                        return Observable.from(items);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CityItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(App.TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(App.TAG, "onError", e);
                    }

                    @Override
                    public void onNext(List<CityItem> cityItems) {
                        Log.d(App.TAG, "City items: " + cityItems.size());
                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void showResult(List<CityItem> result) {
    }

    @Override
    public void onDestroy() {
        Log.d(App.TAG, "onDestroy");
        if (subscriptions != null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
            subscriptions.clear();
        }
    }

    private String buildResultForZip(List<ICommon> list) {
        StringBuilder builder = new StringBuilder();
        for (ICommon item : list) {
            builder.append("\n");
            if (item instanceof AirCompaniesItem) {
                builder.append(((AirCompaniesItem) item).getAirCompany());
            } else if (item instanceof CityItem) {
                builder.append(((CityItem) item).getCity());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private String buildResult(List<CityItem> list) {
        StringBuilder builder = new StringBuilder();
        for (CityItem city : list) {
            builder.append("\n");
            builder.append(city.getCity());
            builder.append("\n");
        }
        return builder.toString();
    }

    private String buildResult(HashMap<String, CityItem> result) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, CityItem> entry : result.entrySet()) {
            builder.append("\n");
            builder.append("Key:   " + entry.getKey());
            builder.append("\n");
            builder.append("Value: " + entry.getValue().getCity());
            builder.append("\n");
        }
        return builder.toString();
    }
}
