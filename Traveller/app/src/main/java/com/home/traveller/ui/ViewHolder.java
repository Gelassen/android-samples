package com.home.traveller.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.home.traveller.App;
import com.home.traveller.R;

/**
 * Created by dmitry.kazakov on 10/2/2015.
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private String path;

    public ViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.card_image);
        itemView.setOnClickListener(this);
    }

    public void updateBackground(final String path) {
        Log.d(App.TAG, "Card image path: " + Uri.parse(path));
        this.path = path;
        Glide.with(itemView.getContext())
                .load(path)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(path));
        DetailsActivity.startActivity(v.getContext(), intent, true);
    }
}
