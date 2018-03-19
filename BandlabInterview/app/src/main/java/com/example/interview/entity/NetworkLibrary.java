package com.example.interview.entity;

import android.content.Context;

import com.example.interview.BuildConfig;
import com.example.interview.R;
import com.example.interview.network.ApiEndpointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The intent of this class is encapsulate details of network library used by app
 * Created by John on 10/3/2016.
 */
public class NetworkLibrary {

    private Retrofit retrofit;

    private ApiEndpointInterface api;

    public void init(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(context.getString(R.string.url));
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(okHttpClient.build());
        retrofit = builder.build();

        api = retrofit.create(ApiEndpointInterface.class);
    }

    public Retrofit getLibrary() {
        return retrofit;
    }

    public ApiEndpointInterface getApi() {
        return api;
    }

}
