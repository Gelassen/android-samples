package com.example.expensestracker.util;

import com.example.expensestracker.model.DriveFiles;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {

    @GET("/files")
    Observable<DriveFiles> getFiles();
}
