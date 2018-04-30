package com.home.vkphotos.photos.detailed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vkphotos.AppApplication;
import com.home.vkphotos.photos.model.ImageBundle;
import com.home.vkphotos.utils.ImageFetcher;
import com.home.vkphotos.R;
import com.home.vkphotos.photos.model.Item;

public class DetailedFragment extends Fragment {

    private static final String EXTRA = "EXTRA";

    private ImageFetcher imageFetcher;

    public static DetailedFragment newInstance(Item item) {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photo_detailed, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppApplication) getActivity().getApplication()).getAppComponent().inject(this);

        Bundle bundle = getArguments();
        Item item = bundle.getParcelable(EXTRA);

        TextView textView = view.findViewById(R.id.text);
        textView.setText(item.getText());

        ImageView imageView = view.findViewById(R.id.detailed);

        ImageBundle imageBundle = new ImageBundle();
        imageBundle.setUrl(item.getPhoto807());
        imageFetcher.submit(imageView, imageBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        imageFetcher.onLowMemoryCall();
    }

    public void setImageFetcher(ImageFetcher imageFetcher) {
        this.imageFetcher = imageFetcher;
    }
}
