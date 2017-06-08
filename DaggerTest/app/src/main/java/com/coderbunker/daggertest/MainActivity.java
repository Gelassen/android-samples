package com.coderbunker.daggertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.coderbunker.daggertest.dto.TestData;
import com.coderbunker.daggertest.list.ListAdapter;
import com.coderbunker.daggertest.view.IView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private RecyclerView list;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
