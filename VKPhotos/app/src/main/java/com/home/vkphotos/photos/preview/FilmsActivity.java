package com.home.vkphotos.photos.preview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.home.vkphotos.App;
import com.home.vkphotos.AppResultReceiver;
import com.home.vkphotos.Images;
import com.home.vkphotos.R;
import com.home.vkphotos.network.GetAllPhotos;
import com.home.vkphotos.network.Status;
import com.home.vkphotos.photos.Item;
import com.squareup.leakcanary.LeakCanary;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class FilmsActivity extends AppCompatActivity implements
        Repository.Listener,
        AppResultReceiver.Listener,
        PhotoAdapter.ShowMoreListener {

    private RecyclerView list;
    private PhotoAdapter adapter;

    private GridLayoutManager layoutManager;

    private AppResultReceiver resultReceiver;

    public static void start(Context context) {
        Intent intent = new Intent(context, FilmsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeakCanary.refWatcher(this);

        resultReceiver = new AppResultReceiver(new Handler());
        resultReceiver.setListener(this);

        adapter = new PhotoAdapter(this);
        adapter.setOnShowMoreListener(this);

        layoutManager = new GridLayoutManager(this, 3);
        list = findViewById(R.id.list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, App.Constants.TOKEN);
        new GetAllPhotos(token.userId, adapter.getItemCount(), token.accessToken)
                .start(this, resultReceiver);
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
    }

    @Override
    public void onSuccess(Status status) {
        List<Item> items = new ArrayList<>();
        for (int id = 0; id < Images.imageThumbUrls.length; id++) {
            Item item = new Item();
            item.setPhoto75(Images.imageThumbUrls[id]);
            item.setId(id);
            items.add(item);
        }
        adapter.updateModel(items);
    }

    @Override
    public void onSuccess(Response resp) {
        adapter.updateModel(App.generateDataset());
    }

    @Override
    public void onError() {
        // TODO show snackbar
        Toast.makeText(this, "Error when obtain the data from server", Toast.LENGTH_SHORT);
    }

    @Override
    public void onShowMore() {
        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, App.Constants.TOKEN);
        // TODO process data
//        new GetAllPhotos(token.userId, adapter.getItemCount(), token.accessToken)
//                .start(this, resultReceiver);
    }
}
