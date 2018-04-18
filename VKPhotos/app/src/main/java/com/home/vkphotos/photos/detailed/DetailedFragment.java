package com.home.vkphotos.photos.detailed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.vkphotos.R;
import com.home.vkphotos.photos.Item;
import com.squareup.picasso.Picasso;

public class DetailedFragment extends Fragment {

    private static final String EXTRA = "EXTRA";

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

        Bundle bundle = getArguments();
        Item item = bundle.getParcelable(EXTRA);
        ImageView imageView = view.findViewById(R.id.detailed);
        Picasso.get()
                .load(item.getPhoto1280())
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
