package com.dataart.school.appinterface;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dataart.school.R;
import com.dataart.school.Schema.Stores;

public class StoreCursorAdapter extends ResourceCursorAdapter {

private int nameIdx;
	
	private int phoneIdx;
	
	private int addressIdx;
	
	private int latIdx;
	
	private int lonIdx;
	
	private Context context;
	
    public StoreCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
        this.context = context;
        initindexes(c);
    }
    
    

    @Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
		initindexes(cursor);
	}


	@Override
    public void bindView(View view, Context context, Cursor cursor) {
		initindexes(cursor);
		
        final String name = cursor.getString(nameIdx);
        final String address = cursor.getString(addressIdx);
        final String phone = cursor.getString(phoneIdx);
        final String lon = cursor.getString(lonIdx);
        final String lat = cursor.getString(latIdx);
        
        ViewHolder holder = new ViewHolder(Double.valueOf(lat), Double.valueOf(lon));
        view.setTag(holder);
        
        TextView storeName = (TextView) view.findViewById(R.id.store_name);
        storeName.setText(name);
        TextView storeAddress = (TextView) view.findViewById(R.id.store_address);
        storeAddress.setText(address);
        TextView storePhone = (TextView) view.findViewById(R.id.store_phone);
        storePhone.setText(phone);
        
        view.setOnClickListener(listener);
    }
	

	private void initindexes(Cursor cursor) {
		if (cursor != null) {
			nameIdx = cursor.getColumnIndex(Stores.STORE_NAME);
			phoneIdx = cursor.getColumnIndex(Stores.PHONE);
			addressIdx = cursor.getColumnIndex(Stores.ADDRESS);
			latIdx = cursor.getColumnIndex(Stores.LATITUDE);
			lonIdx = cursor.getColumnIndex(Stores.LONGITUDE);
		}
				
	}
	
	 View.OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ViewHolder holder = (ViewHolder) v.getTag();
				Double lon = holder.lat;
				Double lat = holder.lon;
				
				Bundle b = new Bundle();
				b.putDouble("LONGITUDE", lon);
				b.putDouble("LATITUDE", lat);
				
				Intent intent = new Intent(context, StoresMapActivity.class);
				intent.putExtras(b);
				context.startActivity(intent);
			}
		};
		
		private class ViewHolder {
			final double lat;
			final double lon;
			
			public ViewHolder(double lat, double lon) {
				this.lat = lat;
				this.lon = lon;
			}
		}

}
