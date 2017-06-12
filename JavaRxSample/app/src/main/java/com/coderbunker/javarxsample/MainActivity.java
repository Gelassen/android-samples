package com.coderbunker.javarxsample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coderbunker.javarxsample.citylist.ListActivity;
import com.coderbunker.javarxsample.operators.OperatorsActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        Button showList = (Button) findViewById(R.id.show_list);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Show list");
                ListActivity.start(v.getContext());
            }
        });

        Button showOperators = (Button) findViewById(R.id.show_operators);
        showOperators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Show operators");
                OperatorsActivity.start(v.getContext());
            }
        });
    }
}
