package com.example.mh_ffmpeg_sample;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Gelassen on 18.05.2016.
 */
public class Test {

    private static final String TEST_VIDEO = "/storage/emulated/0/Android/data/" +
            "com.ef.mediahub.live/files/Pictures/.nomedia/" +
            "Mediahub_VID_20160514_115635-34526518.mp4";

    public static final String getTestVideo() {
        return TEST_VIDEO;
    }

    public static final String getTestDestination(Context context) {
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(publicDir, "test.mp4").toString();
    }

    public static final String getTestDestinationScale(Context context) {
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(publicDir, "test_scale.mp4").toString();
    }
}
