package com.example.interview.videopage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interview.R;
import com.example.interview.Test;
import com.example.interview.model.VideoItem;

/**
 * The intent of this class is present UI and assign it with model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageFragment extends Fragment {

    private static final String EXTRA_PAYLOAD = "EXTRA_PAYLOAD";

    private VideoPagePresenter videoPagePresenter;

    public static Fragment newInstance(VideoItem videoData) {
        Fragment fragment = new VideoPageFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PAYLOAD, videoData);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VideoItem videoData = getArguments().getParcelable(EXTRA_PAYLOAD);

        VideoModel videoModel = new VideoModel();
        videoModel.setUri(videoData.getSource());
        videoModel.setPlaceholderUri(videoData.getThumbnail());

        videoPagePresenter = new VideoPagePresenter(view);
        videoPagePresenter.updateModel(videoModel);
        videoPagePresenter.showPlaceholder(true);
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
