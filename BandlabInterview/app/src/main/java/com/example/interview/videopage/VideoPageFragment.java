package com.example.interview.videopage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.interview.R;
import com.example.interview.Test;

/**
 * The intent of this class is present UI and assign it with model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageFragment extends Fragment {

    private VideoPagePresenter videoPagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VideoModel videoModel = new VideoModel();
        videoModel.setUri(new Test().getUri());

        videoPagePresenter = new VideoPagePresenter(view);
        videoPagePresenter.updateModel(videoModel);
        videoPagePresenter.load();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoPagePresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoPagePresenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPagePresenter.onDestroy();
    }
}
