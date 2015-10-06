package com.home.traveller.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.traveller.R;
import com.home.traveller.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry.kazakov on 10/2/2015.
 */
public class CardsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Card> datasource = new ArrayList<>();

    public void update(List<Card> data) {
        datasource.clear();
        datasource.addAll(data == null ? new ArrayList<Card>() : data);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.component_item_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        Card card = datasource.get(pos);
        viewHolder.updateBackground(card.getPath());
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }
}
