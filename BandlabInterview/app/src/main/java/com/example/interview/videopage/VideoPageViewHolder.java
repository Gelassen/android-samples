package com.example.interview.videopage;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.interview.R;

/**
 * The intent of this class is encapsulate UI and provides API in terms of business logic
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageViewHolder {

    private View root;
    private VideoView videoView;
    private ImageView thumb;
    private ProgressBar loadingPlaceHolder;

    public VideoPageViewHolder(View view) {
        this.root = view.findViewById(R.id.root);
        this.videoView = (VideoView) view.findViewById(R.id.video);
        this.thumb = (ImageView) view.findViewById(R.id.thumb);
        this.loadingPlaceHolder = (ProgressBar) view.findViewById(R.id.progress);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        root.setOnClickListener(onClickListener);
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void showVideo(boolean show) {
        videoView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showPlaceholder(boolean show) {
        thumb.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showLoadingPlaceholder(boolean show) {
        loadingPlaceHolder.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
