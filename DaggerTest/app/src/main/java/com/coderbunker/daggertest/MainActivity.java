package com.coderbunker.daggertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coderbunker.daggertest.dto.TestData;
import com.coderbunker.daggertest.list.ListAdapter;
import com.coderbunker.daggertest.presenter.ListPresenter;
import com.coderbunker.daggertest.view.IView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private RecyclerView list;
    private ListAdapter adapter;

    private ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPresenter = new ListPresenter(this);

        Button uploadData = (Button) findViewById(R.id.upload_data);
        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Upload data");
                listPresenter.processData();
            }
        });

        adapter = new ListAdapter();

        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    public void loadData() {
        List<TestData> data = Test.generateStubData();
        adapter.updateModel(data);
    }

    @Override
    public void showMessage(String msg) {
        Log.d(App.TAG, "showMessage: " + msg);
    }

    @Override
    public void showList() {
        loadData();
    }

    @Override
    public void showList(List<TestData> data) {
        Log.d(App.TAG, "Data to show in list: " + data);
        adapter.updateModel(data);
    }

    @Override
    public void showEmptyList() {
        Log.d(App.TAG, "showEmptyList");
    }
}
