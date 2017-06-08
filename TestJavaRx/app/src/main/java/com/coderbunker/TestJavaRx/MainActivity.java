package com.coderbunker.TestJavaRx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.coderbunker.TestJavaRx.dto.Item;
import com.coderbunker.TestJavaRx.view.IView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new ListPresenter(this);
    }

    @Override
    public void showList(List<String> data) {
        Log.d(App.TAG, "showList: " + data.size());
    }

    @Override
    public void showEmptyList() {
        Log.d(App.TAG, "showEmptyList");
    }
}
