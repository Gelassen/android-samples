package com.example.interview.screens.phonenumbers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.interview.R;

public class PhoneNumbersViewHolder extends RecyclerView.ViewHolder {

    private TextView phone;
    private TextView owner;
    private TextView price;

    public PhoneNumbersViewHolder(View itemView) {
        super(itemView);

        phone = (TextView) itemView.findViewById(R.id.phone_item_number);
        owner = (TextView) itemView.findViewById(R.id.phone_item_owner);
        price = (TextView) itemView.findViewById(R.id.phone_item_price);
    }

    public void setPhone(String value) {
        phone.setText(value);
    }

    public void setOwner(String value) {
        owner.setText(value);
    }

    public void setPrice(String value) {
        price.setText(value);
    }

    public void setClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
