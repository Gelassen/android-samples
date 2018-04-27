package com.home.vkphotos.photos.preview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.home.vkphotos.App;
import com.home.vkphotos.AppResultReceiver;
import com.home.vkphotos.R;
import com.home.vkphotos.network.GetAllPhotos;
import com.home.vkphotos.network.Status;
import com.home.vkphotos.photos.model.Item;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;


public class FilmsActivity extends AppCompatActivity implements
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
        adapter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.onPause();
        resultReceiver.setListener(null);
    }

    @Override
    public void onSuccess(Status status) {
        ArrayList<Item> items = (ArrayList<Item>) status.getPayload();
//        items.addAll(App.generateDataset());
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
