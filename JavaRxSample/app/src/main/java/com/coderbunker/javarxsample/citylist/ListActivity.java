package com.coderbunker.javarxsample.citylist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.R;
import com.coderbunker.javarxsample.citylist.dto.CityItem;
import com.coderbunker.javarxsample.citylist.view.CityListAdapter;
import com.coderbunker.javarxsample.citylist.view.CityListPresenter;
import com.coderbunker.javarxsample.citylist.view.IPresenter;
import com.coderbunker.javarxsample.citylist.view.IView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements IView {

    public static void start(Context context) {
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);
    }

    private CityListAdapter adapter;
    private IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CityListAdapter();

        Button button = (Button) findViewById(R.id.upload_data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Upload data: ");
                presenter.triggerEvent();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new CityListPresenter(this);
        presenter.triggerEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showList(List<CityItem> model) {
        Log.d(App.TAG, "Show list for " + model.size() + " items");
        adapter.updateModel(model);
    }

    @Override
    public void showEmptyList() {
        Log.d(App.TAG, "Show empty list");
    }

    @Override
    public void showError() {
        Log.d(App.TAG, "Show error");
        Toast.makeText(this, "Show error", Toast.LENGTH_SHORT).show();
    }
}
