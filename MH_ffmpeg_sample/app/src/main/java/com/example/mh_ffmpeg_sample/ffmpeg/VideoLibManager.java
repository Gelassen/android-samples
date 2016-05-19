package com.example.mh_ffmpeg_sample.ffmpeg;

import android.content.Context;
import android.util.Log;

import com.example.mh_ffmpeg_sample.App;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

/**
 * Created by Gelassen on 18.05.2016.
 */
public class VideoLibManager implements FFmpegLoadBinaryResponseHandler {

    public void init(Context context) throws FFmpegNotSupportedException {
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        ffmpeg.loadBinary(this);
    }

    @Override
    public void onFailure() {
        Log.d(App.TAG, "FFmpeg init onFailure");
    }

    @Override
    public void onSuccess() {
        Log.d(App.TAG, "FFmpeg init onSuccess");
    }

    @Override
    public void onStart() {
        Log.d(App.TAG, "FFmpeg init onStart");
    }

    @Override
    public void onFinish() {
        Log.d(App.TAG, "FFmpeg init onFinish");
    }

    public void runCommandWithListener(Context context, final String[] command,
                                       FFmpegExecuteResponseHandler listener) throws FFmpegCommandAlreadyRunningException {
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        ffmpeg.execute(command, listener);
    }
}
