package com.coderbunker.javarxsample.operators;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.BaseActivity;
import com.coderbunker.javarxsample.R;

public class OperatorsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, OperatorsActivity.class);
        context.startActivity(intent);
    }

    private Button runMap;
    private TextView resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        runMap = (Button) findViewById(R.id.operators_map);
        runMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "Run operators");
            }
        });

        resultView = (TextView) findViewById(R.id.show_operators);
    }
}
