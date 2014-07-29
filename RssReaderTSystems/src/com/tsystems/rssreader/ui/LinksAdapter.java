package com.tsystems.rssreader.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.sax.StartElementListener;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsystems.rssreader.DetailActivity;
import com.tsystems.rssreader.R;
import com.tsystems.rssreader.database.Schema;

public class LinksAdapter extends ResourceCursorAdapter {

	private int linkIdx;
	
	private int titleIdx;
	
	public LinksAdapter(Context context, int layout, Cursor c, int flags) {
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
		holder.setLink(cursor.getString(linkIdx));
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = super.newView(context, cursor, parent);
		view.setTag(new ViewHolder(
				(TextView) view.findViewById(R.id.link)));
		view.setOnClickListener(listener);
		return view;
	}

	private void initColumns(Cursor c) {
		if (null != c) {
			linkIdx = c.getColumnIndex(Schema.Links.LINK);
			titleIdx = c.getColumnIndex(Schema.Links.TITLE);
		}
	}
	
	View.OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ViewHolder holder = (ViewHolder) v.getTag();
			String link = holder.getLink();
			//TODO run link
			Intent intent = new Intent(mContext, DetailActivity.class);
			intent.putExtra(DetailActivity.LINK, link);
			mContext.startActivity(intent);
		}
	};
	
	private class ViewHolder {

		final TextView title;
		private String link;

		public ViewHolder(final TextView title) {
			this.title = title;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

	}

}
