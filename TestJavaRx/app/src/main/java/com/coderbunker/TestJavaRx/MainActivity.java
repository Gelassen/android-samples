package com.coderbunker.TestJavaRx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.coderbunker.TestJavaRx.dto.Item;
import com.coderbunker.TestJavaRx.view.IView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IView {

    private ListPresenter presenter;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getComponent().inject(this);

        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "onClick");
                Log.d(App.TAG, "Context " + (context == null));
            }
        });

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
