package com.example.expensestracker.service;

import com.example.expensestracker.util.Api;
import com.example.expensestracker.util.AppApplication;
import com.example.expensestracker.model.DriveFiles;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gelassen on 02.03.2018.
 */

public class NetworkService implements Api {

    private Api service;

    public NetworkService(AppApplication application) {
        service = application.getService();
    }

    @Override
    public Observable<DriveFiles> getFiles() {
        return service.getFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
