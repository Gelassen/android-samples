package com.example.interview.videopage;

import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.interview.R;

/**
 * The intent of this class is encapsulate UI and provides API in terms of business logic
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageViewHolder {

    private VideoView videoView;
    private ImageView thumb;

    public VideoPageViewHolder(View view) {
        this.videoView = (VideoView) view.findViewById(R.id.video);
        this.thumb = (ImageView) view.findViewById(R.id.thumb);
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
}
