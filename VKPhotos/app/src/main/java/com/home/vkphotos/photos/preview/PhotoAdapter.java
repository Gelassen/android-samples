package com.home.vkphotos.photos.preview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.vkphotos.R;
import com.home.vkphotos.photos.Item;
import com.home.vkphotos.photos.detailed.DetailedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private final ArrayList<Item> items = new ArrayList<>();

    public void updateModel(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void reset() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_preview, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        final Item item = items.get(position);

        Picasso.get()
                .load(item.getPhoto1280())
                .fit()
                .centerCrop()
                .into(holder.preview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedActivity.start(view.getContext(), items, item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView preview;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.preview);
        }
    }
}
