package com.example.interview.videopage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.interview.App;
import com.example.interview.R;
import com.example.interview.convertors.VideoDimensToScaledDimenConverter;
import com.example.interview.entity.Events;
import com.example.interview.model.VideoItem;

/**
 * The intent of this class is present UI and assign it with model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageFragment extends Fragment implements VideoPagePresenter.Callbacks {

    private static final String EXTRA_PAYLOAD = "EXTRA_PAYLOAD";
    private static final String EXTRA_POSITION = "EXTRA_POSITION";

    private VideoPageInteractor interactor;
    private VideoPagePresenter videoPagePresenter;
    private Events events;

    public static Fragment newInstance(VideoItem videoData, int position) {
        Fragment fragment = new VideoPageFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PAYLOAD, videoData);
        args.putInt(EXTRA_POSITION, position);
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
        interactor = new VideoPageInteractor();

        VideoModel videoModel = new VideoModel();
        videoModel.setUri(videoData.getSource());
        videoModel.setPlaceholderUri(videoData.getThumbnail());

        Point point = interactor.getDisplaySize(getActivity());
        videoPagePresenter = new VideoPagePresenter(view, new VideoDimensToScaledDimenConverter(point.x, point.y), this);
        videoPagePresenter.updateModel(videoModel);
        videoPagePresenter.showPlaceholder(true);

        events = new Events();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoPagePresenter.onResume();
        events.registerListener(getContext(), broadcastReceiver, Events.EVENT_PAGE_CHANGED);
    }

    @Override
    public void onPause() {
        super.onPause();
        videoPagePresenter.onPause();
        events.unregisterListener(getContext(), broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPagePresenter.onDestroy();
    }

    @Override
    public void onReadyForStart() {
//        videoPagePresenter.load();
    }

    @Override
    public void onError() {
        Log.e(App.TAG, "Media player error");
        videoPagePresenter.showPlaceholder(false);
        if (isAdded())
            Toast.makeText(getContext(), getString(R.string.error_failed_media), Toast.LENGTH_SHORT)
                    .show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (shallBeSkipped(intent)) return;
            videoPagePresenter.onVideoPause();
        }

        /**
         * App skips event for current selected page
         * */
        private boolean shallBeSkipped(Intent intent) {
            Log.d(App.TAG, "VideoPageFragment receive page change event");
            Events events = new Events();
            return getArguments().getInt(EXTRA_POSITION) == events.getPagePosition(intent);
        }
    };

}
