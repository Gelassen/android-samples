package com.home.vkphotos.photos.preview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.home.vkphotos.App;
import com.home.vkphotos.R;
import com.vk.sdk.VKAccessToken;

public class FilmsActivity extends AppCompatActivity implements Repository.Listener {

    private Repository repository;

    private RecyclerView list;
    private PhotoAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FilmsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO init layout with images
        // TODO obtain images
        // TODO support pagination

        repository = new Repository(this);

        adapter = new PhotoAdapter();
        list = findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, 3));
        list.setAdapter(adapter);

        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, App.Constants.TOKEN);
        repository.getData(token.userId);
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
}
