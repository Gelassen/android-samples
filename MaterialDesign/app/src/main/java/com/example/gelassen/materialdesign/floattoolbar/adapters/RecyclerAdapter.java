package com.example.gelassen.materialdesign.floattoolbar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gelassen.materialdesign.R;
import com.example.gelassen.materialdesign.floattoolbar.viewholders.RecyclerHeaderViewHolder;
import com.example.gelassen.materialdesign.floattoolbar.viewholders.RecyclerItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by Gelassen on 04.04.2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    private List<String> data = new ArrayList<>();

    public RecyclerAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (type) {
            case TYPE_HEADER:
                View view = inflater.inflate(R.layout.recycler_header, parent, false);
                return new RecyclerHeaderViewHolder(view);
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.recycler_item, parent, false);
                return new RecyclerItemViewHolder(view, (android.widget.TextView) view.findViewById(R.id.itemTextView));
        }
        throw new RuntimeException("Did you add implementation for a new view?");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (position == 0) return;

        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        String itemText = data.get(position - 1); // header
        holder.setItemText(itemText);

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }
}
