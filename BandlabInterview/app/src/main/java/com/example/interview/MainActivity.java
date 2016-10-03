package com.example.interview;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.interview.network.commands.GetInitialVideoPageCommand;
import com.example.interview.videopage.VideoPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, new VideoPageFragment())
                .commit();

//        new GetInitialVideoPageCommand()
//                .start(this, null);
    }
}
