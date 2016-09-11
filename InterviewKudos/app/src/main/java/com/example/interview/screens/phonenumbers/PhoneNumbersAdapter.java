package com.example.interview.screens.phonenumbers;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interview.R;
import com.example.interview.model.CursorTelNumbersDatasource;
import com.example.interview.model.IDataSource;
import com.example.interview.model.TelNumber;
import com.example.interview.screens.phonenumbers.domain.PhoneNumbersService;

/**
 * The intent of this class is encapsulate mapping between storage layer and UI layer
 *
 * Created by John on 9/11/2016.
 */
public class PhoneNumbersAdapter extends RecyclerView.Adapter<PhoneNumbersViewHolder> {

    private Context context;
    private IDataSource dataSource;

    private PhoneNumbersService service;

    public PhoneNumbersAdapter(Context context) {
        this.context = context;
        this.dataSource = new CursorTelNumbersDatasource();
        this.service = new PhoneNumbersService();
    }

    @Override
    public PhoneNumbersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_row_tel_number, parent, false);
        return new PhoneNumbersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneNumbersViewHolder holder, int position) {
        final TelNumber telNumber = (TelNumber) dataSource.getItemForPosition(position);
        holder.setPrice(
                telNumber.getPhoneNumberPrice()
        );
        holder.setPhone(
                telNumber.getPhoneNumber()
        );
        holder.setOwner(
                telNumber.getPhoneNumberOwner()
        );
        holder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.onPhoneNumberClick(view.getContext(), telNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.getItemCount();
    }

    public void updateModel(Cursor cursor) {
        // according to current requirements we don't need to cover partial update,
        // so there is not need in notifyItemRangeChanged()
        dataSource.update(cursor);
        notifyDataSetChanged();
    }
}
