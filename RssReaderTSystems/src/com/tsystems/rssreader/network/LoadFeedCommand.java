package com.tsystems.rssreader.network;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.tsystems.rssreader.database.Feed;
import com.tsystems.rssreader.database.RssReaderContentProvider;
import com.tsystems.rssreader.database.Schema;

public class LoadFeedCommand extends Command {

	public LoadFeedCommand(String uri) {
		super(uri);
	}

	@Override
	protected HttpUriRequest getRequest() {
		HttpGet request = new HttpGet(uri);
		// TODO add parameters if it is required
		return request;
	}

	@Override
	protected void handleResults(Context context, HttpResponse response) {
		try {
			RssParser parser = new RssParser(uri);
			List<Feed> feeds = parser.parse(response.getEntity().getContent());
			// TODO add result to the database
			final int size = feeds.size();
			ContentValues[] data = new ContentValues[size];
			Feed feed;
			for (int i = 0; i < size; i++) {
				feed = feeds.get(i);
				ContentValues values = new ContentValues(6);
				values.put(Schema.Feeds.GUID, feed.getGuid());
				values.put(Schema.Feeds.TITLE, feed.getTitle());
				values.put(Schema.Feeds.DESC, feed.getDesc());
				values.put(Schema.Feeds.LINK, feed.getLink());
				values.put(Schema.Feeds.VIEWED, feed.getViewed());
				values.put(Schema.Feeds.PUB_DATE, feed.getPubDate());
				data[i] = values;
			}
			context.getContentResolver().bulkInsert(RssReaderContentProvider.FEEDS_URI, data);
		} catch (Exception e) {
			// Yes, it is a bad practice to swallow all types of exceptions,
			// but I whatever do the same logic - write to log - so in this case
			// it is OK. 
			Log.d(Schema.TAG, "Failed to parse and save results for " + uri, e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(uri);
	}

	public static final Parcelable.Creator<LoadFeedCommand> CREATOR = 
			new Parcelable.Creator<LoadFeedCommand>() {

		public LoadFeedCommand createFromParcel(Parcel source) {
			final String uri = source.readString();
			return new LoadFeedCommand(uri);
		}

		public LoadFeedCommand[] newArray(int size) {
			return new LoadFeedCommand[size];
		}

	};
}
