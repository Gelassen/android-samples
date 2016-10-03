package com.example.interview.videopage;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import com.example.interview.App;


/**
 * The intent of this class is encapsulate UI and provides API in terms of business logic
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

        viewHolder.getVideoView().setOnErrorListener(this);
        viewHolder.getVideoView().setOnPreparedListener(this);
        viewHolder.getVideoView().setOnClickListener(this);
        viewHolder.getVideoView().setKeepScreenOn(true);
    }

    public void updateModel(VideoModel videoModel) {
        this.model = videoModel;
    }

    public void load() {
        if (model == null || model.isInvalid()) return;
        viewHolder.getVideoView()
                .setVideoPath(model.getUri());
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(App.TAG, "onPrepared");
        viewHolder.getVideoView().start();
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
        if (isPlaying) {
            onPause();
        } else {
            onResume();
        }
    }

    public void onResume() {
        Log.d(App.TAG, "onResume");
        viewHolder.getVideoView().resume();
        viewHolder.showVideo(true);
        viewHolder.showPlaceholder(false);
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
}
