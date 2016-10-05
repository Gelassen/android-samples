package com.example.interview.videopage;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.interview.App;
import com.example.interview.R;
import com.example.interview.convertors.VideoDimensToScaledDimenConverter;

import java.io.IOException;
import java.lang.ref.WeakReference;


/**
 * The intent of this class is encapsulate video page UI and provides API in terms of business logic
 *
 * Created by John on 10/3/2016.
 */
public class VideoPagePresenter implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        TextureView.SurfaceTextureListener,
        View.OnClickListener {

    public interface Callbacks {
        void onReadyForStart();
    }

    private VideoPageViewHolder viewHolder;
    private VideoModel model;

    private MediaPlayer mediaPlayer;
    private VideoDimensToScaledDimenConverter converter;
    private WeakReference<Callbacks> callbacksRef;

    public VideoPagePresenter(View view, VideoDimensToScaledDimenConverter converter, Callbacks listener) {
        viewHolder = new VideoPageViewHolder(view);
        viewHolder.setOnClickListener(this);
        viewHolder.getVideoView().setKeepScreenOn(true);
        viewHolder.getVideoView().setSurfaceTextureListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);

        this.converter = converter;
        callbacksRef = new WeakReference<>(listener);
    }

    public void updateModel(VideoModel videoModel) {
        this.model = videoModel;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(App.TAG, "Player onPrepared");
        converter.init(new Pair<>(mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight()));
        applyScaleMatrix(converter.convert());
        mediaPlayer.start();

        viewHolder.showPlaceholder(false);
        viewHolder.showLoadingPlaceholder(false);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.d(App.TAG, "onError");
        // TODO show error
        return false;
    }

    @Override
    public void onClick(View view) {
        boolean isPlaying = mediaPlayer.isPlaying();
        Log.d(App.TAG, "onClick:isPlaying " + isPlaying );
        if (model.isFirstStart()) {
            load();
            model.firstStartHappened();
        } else if (isPlaying) {
            onVideoPause();
        } else {
            onVideoPlay();
        }
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

    public void showPlaceholder(boolean show) {
        viewHolder.showPlaceholder(show);
        Glide.with(viewHolder.getVideoView().getContext())
                .load(model.getPlaceholderUri())
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e(App.TAG, "onException for model: " + model);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d(App.TAG, "onResourceReady for model: " + model);
                        return false;
                    }
                })
                .into(viewHolder.getPlaceholderView());
    }

    public void onVideoPlay() {
        Log.d(App.TAG, "onVideoPlay");
        mediaPlayer.start();
        viewHolder.showVideo(true);
        viewHolder.showPlaceholder(false);
    }

    public void onVideoPause() {
        Log.d(App.TAG, "onVideoPause");
        mediaPlayer.pause();
        model.saveProgressState(mediaPlayer.getCurrentPosition());
    }

    public void onResume() {
        // TODO restore previous video state
    }

    public void onPause() {
        Log.d(App.TAG, "onPause");
        mediaPlayer.pause();
        viewHolder.showVideo(false);
    }

    public void onDestroy() {
        Log.d(App.TAG, "onDestroy");
        mediaPlayer.stop();
    }

    public void load() {
        if (!viewHolder.getVideoView().isAvailable()
                || model == null || model.isInvalid()) return; // wait for texture view availability
        try {
            mediaPlayer.setDataSource(model.getUri());
            mediaPlayer.prepareAsync();
            viewHolder.showLoadingPlaceholder(true);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to load video", e);
        }
    }

    private void applyScaleMatrix(Pair<Float, Float> scaled) {
        Pair<Integer, Integer> display = converter.getDisplayDimensions();
        Matrix transform = new Matrix();
        transform.setScale(scaled.first, scaled.second, display.first / 2, display.second / 2);
        viewHolder.getVideoView().setTransform(transform);
    }
}
