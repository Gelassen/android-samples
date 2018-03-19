package com.example.mh_ffmpeg_sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mh_ffmpeg_sample.ffmpeg.FfmpegCommandBuilder;
import com.example.mh_ffmpeg_sample.ffmpeg.FfmpegCommands;
import com.example.mh_ffmpeg_sample.ffmpeg.VideoLibManager;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class MainActivity extends AppCompatActivity {

    private FfmpegCommands ffmpegCommands;
    private VideoLibManager videoLibManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        ffmpegCommands = new FfmpegCommands();
        videoLibManager = new VideoLibManager();
        try {
            videoLibManager.init(this);
        } catch (FFmpegNotSupportedException e) {
            Log.e(App.TAG, "Failed to init the library", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConvert(View view) {
        try {
            String[] command = ffmpegCommands.getCompressCommand(
                    Test.getTestVideo(),
                    Test.getTestDestination(this)
            );
            videoLibManager.runCommandWithListener(this, command, listener);
        } catch (FFmpegCommandAlreadyRunningException e) {
            Log.e(App.TAG, "Failed to run command", e);
        }
    }

    public void onScale(View view) {
        try {
            String[] command = ffmpegCommands.getCompressCommand(
                    Test.getTestVideo(),
                    Test.getTestDestination(this)
            );
            videoLibManager.runCommandWithListener(this, command, listener);
        } catch (FFmpegCommandAlreadyRunningException e) {
            Log.e(App.TAG, "Failed to run command", e);
        }
    }

    private FFmpegExecuteResponseHandler listener = new FFmpegExecuteResponseHandler() {
        @Override
        public void onSuccess(String s) {
            Log.d(App.TAG, "Command onSuccess: " + s);
        }

        @Override
        public void onProgress(String s) {
            Log.d(App.TAG, "Command onProgress: " + s);
        }

        @Override
        public void onFailure(String s) {
            Log.d(App.TAG, "Command onFailure: " + s);
        }

        @Override
        public void onStart() {
            Log.d(App.TAG, "Command onStart");
        }

        @Override
        public void onFinish() {
            Log.d(App.TAG, "Command onFinish");
        }
    };
}
