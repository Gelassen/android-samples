package com.example.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.interview.network.commands.GetInitialVideoPageCommand;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetInitialVideoPageCommand()
                .start(this, null);
    }
}
