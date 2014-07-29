package com.tsystems.rssreader.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsystems.rssreader.R;
import com.tsystems.rssreader.database.Schema;

public class FeedsAdapter extends ResourceCursorAdapter {

	private int linkIdx;
	private int guidIdx;
	private int titleIdx;
	private int descIdx;
	private int pubDateIdx;
	private int viewedIdx;
	
	public FeedsAdapter(Context context, int layout, Cursor c, int flags) {
		super(context, layout, c, flags);
		initColumns(c);
	}

	@Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
		swapCursor(cursor);
		initColumns(cursor);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(cursor.getString(titleIdx));
		holder.date.setText(cursor.getString(pubDateIdx));
		holder.desc.setText(cursor.getString(descIdx));
		holder.setGuid(cursor.getString(guidIdx));
		holder.setViewed(cursor.getString(viewedIdx));
		//TODO set color according to viewed value
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = super.newView(context, cursor, parent);
		view.setTag(new ViewHolder(
				view.findViewById(R.id.feed_title),
				view.findViewById(R.id.feed_pub_date),
				view.findViewById(R.id.feed_desc)));
		view.setOnClickListener(listener);
		return view;
	}

	private void initColumns(Cursor c) {
		if (null != c) {
			linkIdx = c.getColumnIndex(Schema.Feeds.LINK);
			guidIdx = c.getColumnIndex(Schema.Feeds.GUID);
			titleIdx = c.getColumnIndex(Schema.Feeds.TITLE);
			descIdx = c.getColumnIndex(Schema.Feeds.DESC);
			pubDateIdx = c.getColumnIndex(Schema.Feeds.PUB_DATE);
			viewedIdx = c.getColumnIndex(Schema.Feeds.VIEWED);
		}
	}
	
	View.OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ViewHolder holder = (ViewHolder) v.getTag();
			String link = holder.getGuid();
			//TODO start web browser
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
			mContext.startActivity(intent);
			//TODO update viewed state in database
			
		}
	};
	
	private class ViewHolder {

		final TextView title;
		final TextView date;
		final TextView desc;
		
		private String guid;
		private String viewed;

		public ViewHolder(final View title, final View date, final View desc) {
			this.title = (TextView) title;
			this.date = (TextView) date;
			this.desc = (TextView) desc;
		}

		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public String getViewed() {
			return viewed;
		}

		public void setViewed(String viewed) {
			this.viewed = viewed;
		}
		
	}

}
