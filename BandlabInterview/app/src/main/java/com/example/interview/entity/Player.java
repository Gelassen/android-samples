package com.example.interview.entity;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.example.interview.App;
import com.example.interview.R;
import com.example.interview.convertors.VideoDimensToScaledDimenConverter;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * The intent of this class is encapsulate implementation of video player
 *
 * Created by John on 10/5/2016.
 */
public class Player implements
        TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    public interface Callbacks {
        void onReadyForStart();
    }

    private MediaPlayer mediaPlayer;
    private TextureView video;

    private VideoDimensToScaledDimenConverter converter;
    private WeakReference<Callbacks> callbacksRef;

    public Player(View view, VideoDimensToScaledDimenConverter converter, Callbacks callbacks) {
        this.converter = converter;

        video = (TextureView) view.findViewById(R.id.video);
        video.setSurfaceTextureListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        callbacksRef = new WeakReference<>(callbacks);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d(App.TAG, "onSurfaceTextureAvailable");
        Surface surface = new Surface(surfaceTexture);
        mediaPlayer.setSurface(surface);
        if (callbacksRef.get() != null) callbacksRef.get().onReadyForStart();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        // no op
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        // no op
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        // no op
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.d(App.TAG, "onError");
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(App.TAG, "Player onPrepared");
        converter.init(new Pair<>(mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight()));
        applyScaleMatrix(converter.convert());
        mediaPlayer.start();
    }

    public void load(final String data) throws IOException {
        if (!video.isAvailable()) return; // wait for texture view availability
        mediaPlayer.setDataSource(data);
        mediaPlayer.prepareAsync();
    }

    private void applyScaleMatrix(Pair<Float, Float> scaled) {
        Pair<Integer, Integer> display = converter.getDisplayDimensions();
        Matrix transform = new Matrix();
        transform.setScale(scaled.first, scaled.second, display.first / 2, display.second / 2);
        video.setTransform(transform);
    }
}
