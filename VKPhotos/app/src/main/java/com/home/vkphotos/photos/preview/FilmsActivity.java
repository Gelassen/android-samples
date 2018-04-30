package com.home.vkphotos.photos.preview;


import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.home.vkphotos.App;
import com.home.vkphotos.AppApplication;
import com.home.vkphotos.AppResultReceiver;
import com.home.vkphotos.LifeCycleListener;
import com.home.vkphotos.R;
import com.home.vkphotos.network.GetAllPhotos;
import com.home.vkphotos.network.Status;
import com.home.vkphotos.photos.model.Item;
import com.squareup.leakcanary.LeakCanary;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class FilmsActivity extends AppCompatActivity implements
        AppResultReceiver.Listener,
        PhotoAdapter.ShowMoreListener {

    private RecyclerView list;
    private PhotoAdapter adapter;
    private GridLayoutManager layoutManager;
    private AppResultReceiver resultReceiver;

    @Inject
    LifeCycleListener lifeCycleListener;

    public static void start(Context context) {
        Intent intent = new Intent(context, FilmsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AppApplication) getApplication()).getAppComponent().inject(this);

        resultReceiver = new AppResultReceiver(new Handler());
        resultReceiver.setListener(this);

        adapter = new PhotoAdapter(this);
        adapter.setOnShowMoreListener(this);

        layoutManager = new GridLayoutManager(this, 3);
        list = findViewById(R.id.list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    adapter.onStopScroll();
                } else {
                    adapter.onScroll();
                }
            }
        });


        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, App.Constants.TOKEN);
        new GetAllPhotos(token.userId, adapter.getItemCount(), token.accessToken)
                .start(this, resultReceiver);

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                lifeCycleListener.notifyOnLowMemory();
                break;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        resultReceiver.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        resultReceiver.setListener(null);
        adapter.onPause();
    }

    @Override
    public void onSuccess(Status status) {
        ArrayList<Item> items = (ArrayList<Item>) status.getPayload();
        adapter.updateModel(items);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error when obtain the data from server", Toast.LENGTH_SHORT);
    }

    @Override
    public void onShowMore() {
        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, App.Constants.TOKEN);
        new GetAllPhotos(token.userId, adapter.getItemCount(), token.accessToken)
                .start(this, resultReceiver);
    }
}
