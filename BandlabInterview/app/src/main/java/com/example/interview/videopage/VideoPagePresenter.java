package com.example.interview.videopage;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.interview.App;
import com.example.interview.R;


/**
 * The intent of this class is encapsulate video page UI and provides API in terms of business logic
 *
 * Created by John on 10/3/2016.
 */
public class VideoPagePresenter implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        View.OnClickListener {

    private VideoPageViewHolder viewHolder;
    private VideoModel model;

    public VideoPagePresenter(View view) {
        viewHolder = new VideoPageViewHolder(view);
        viewHolder.setOnClickListener(this);
        viewHolder.getVideoView().setOnErrorListener(this);
        viewHolder.getVideoView().setOnPreparedListener(this);
        viewHolder.getVideoView().setKeepScreenOn(true);
    }

    public void updateModel(VideoModel videoModel) {
        this.model = videoModel;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(App.TAG, "onPrepared");
        viewHolder.getVideoView().start();
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
        boolean isPlaying = viewHolder.getVideoView().isPlaying();
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
        viewHolder.getVideoView().resume();
        viewHolder.showVideo(true);
        viewHolder.showPlaceholder(false);
    }

    public void onVideoPause() {
        Log.d(App.TAG, "onVideoPause");
        viewHolder.getVideoView().pause();
        viewHolder.showVideo(false);
        viewHolder.showPlaceholder(true);
    }

    public void onResume() {
        // TODO restore previous video state
    }

    public void onPause() {
        Log.d(App.TAG, "onPause");
        viewHolder.getVideoView().pause();
        viewHolder.showVideo(false);
        viewHolder.showPlaceholder(true);
    }

    public void onDestroy() {
        Log.d(App.TAG, "onDestroy");
        viewHolder.getVideoView().stopPlayback();
    }

    private void load() {
        Log.d(App.TAG, "Load");
        if (model == null || model.isInvalid()) return;
        viewHolder.getVideoView()
                .setVideoPath(model.getUri());
        viewHolder.showLoadingPlaceholder(true);
    }
}
