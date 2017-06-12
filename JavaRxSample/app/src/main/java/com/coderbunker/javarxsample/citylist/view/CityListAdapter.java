package com.coderbunker.javarxsample.citylist.view;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.R;
import com.coderbunker.javarxsample.citylist.dto.CityItem;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter {

    private List<CityItem> model = new ArrayList<>();

    public void updateModel(List<CityItem> model) {
        this.model = model;
        notifyDataSetChanged();
        Log.d(App.TAG, "updateModel: " + model.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(App.TAG, "onBindViewHolder");
        CityItem item = model.get(position);
        ((ViewHolder) holder).text.setText(item.getCity());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
