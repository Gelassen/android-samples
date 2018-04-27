package com.home.vkphotos.photos.preview;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.vkphotos.App;
import com.home.vkphotos.AppApplication;
import com.home.vkphotos.photos.model.ImageBundle;
import com.home.vkphotos.utils.ImageFetcher;
import com.home.vkphotos.R;
import com.home.vkphotos.photos.EndlessRecyclerViewScrollListener;
import com.home.vkphotos.photos.model.Item;
import com.home.vkphotos.photos.detailed.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    @Inject
    ImageFetcher imageFetcher;

    public PhotoAdapter(Activity context) {
        imageFetcher = new ImageFetcher(context);

        ((AppApplication) context.getApplication()).getAppComponent().inject(this);
    }

    public interface ShowMoreListener {
        void onShowMore();
    }

    private ShowMoreListener onShowMoreListener;

    private final ArrayList<Item> items = new ArrayList<>();

    public void updateModel(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnShowMoreListener(ShowMoreListener listener) {
        onShowMoreListener = listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_preview, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        final Item item = items.get(position);

        ImageBundle bundle = new ImageBundle();
        bundle.setUrl(item.getPhoto604());
        imageFetcher.submit(holder.preview, bundle);

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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        initScrollListener(recyclerView, layoutManager);
    }

    public void onResume() {
        imageFetcher.onResume();
    }

    public void onPause() {
        imageFetcher.onPause();
    }

    private void initScrollListener(RecyclerView recyclerView, final LinearLayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(App.TAG, "onLoadMore: " + page + "Total: " + totalItemsCount);
                onShowMoreListener.onShowMore();
            }
        });
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView preview;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.preview);
        }
    }
}
