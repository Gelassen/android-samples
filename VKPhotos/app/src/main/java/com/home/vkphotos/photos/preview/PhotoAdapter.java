package com.home.vkphotos.photos.preview;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vkphotos.App;
import com.home.vkphotos.ImageBundle;
import com.home.vkphotos.ImageFetcherNew;
import com.home.vkphotos.R;
import com.home.vkphotos.photos.EndlessRecyclerViewScrollListener;
import com.home.vkphotos.photos.Item;
import com.home.vkphotos.photos.detailed.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ImageFetcherNew imageFetcher;

    public PhotoAdapter(Context context) {
        imageFetcher = new ImageFetcherNew(context);
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
        final long sstart = System.currentTimeMillis();
        Log.d(App.TAG, "onBindViewHolder:start " + sstart);
        final Item item = items.get(position);

        Log.d(App.TAG, "Id: " + item.getId());
//        Picasso.get()
//                .load(item.getPhoto1280())
//                .fit()
//                .centerCrop()
//                .into(holder.preview);

        ImageBundle bundle = new ImageBundle();
        bundle.setUrl(item.getPhoto75());

        imageFetcher.submit(holder.preview, bundle);
//        imageFetcher.process(holder.preview, bundle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedActivity.start(view.getContext(), items, item.getId());
            }
        });
        holder.order.setText(String.valueOf(item.getId()));
        final long end = System.currentTimeMillis() - sstart;
        Log.d(App.TAG, "onBindViewHolder:end:delta " + end);
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

    private void initScrollListener(RecyclerView recyclerView, final LinearLayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(App.TAG, "onLoadMore: " + page + "Total: " + totalItemsCount);
                onShowMoreListener.onShowMore();
            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (onShowMoreListener != null /*&& !blockShowMoreEvent*/) {
//                    int totalItemCount = layoutManager.getItemCount();
//                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//                    int numVisibleItem = lastVisibleItem - firstVisibleItem;
//
//                    int leftPart = totalItemCount - lastVisibleItem;
//                    int rightPart = 2 * numVisibleItem;
//                    if (leftPart < rightPart) {
//                        blockShowMoreEvent = true;
//                        onShowMoreListener.onShowMore();
//                    }
//                }
//            }
//        });
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView preview;
        private final TextView order;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.preview);
            order = itemView.findViewById(R.id.order);
        }
    }
}
