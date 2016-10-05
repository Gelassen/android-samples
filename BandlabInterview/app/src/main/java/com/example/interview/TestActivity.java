package com.example.interview;

import android.app.Activity;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.VideoView;

import com.example.interview.convertors.VideoDimensToScaledDimenConverter;
import com.example.interview.entity.Player;

import java.io.IOException;

/**
 * Created by John on 10/5/2016.
 */
public class TestActivity extends Activity implements Player.Callbacks {

    private static final String DATA = "https://currentcnmedia.s3.cn-north-1.amazonaws.com.cn/8ddc44af073c41cf8a78e6c725004b57.mp4";

    private Player player;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        player = new Player(
                findViewById(R.id.video),
                new VideoDimensToScaledDimenConverter(point.x, point.y),
                this);

        Test.getTestModel();
    }

    @Override
    public void onReadyForStart() {
        try {
            player.load(DATA);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to load video");
        }
    }
}
