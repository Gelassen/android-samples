package com.tsystems.rssreader.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.Parcelable;
import android.util.Log;

import com.tsystems.rssreader.database.Schema;

/**
 * Yes, this is overhead for a simple task as Rss reader, but I just want 
 * to show you my skills that could be applied in production projects.
 * */
public abstract class Command implements Parcelable{
	
	protected static final AndroidHttpClient client = AndroidHttpClient.newInstance(Schema.TAG);
	
	protected String uri;
	
	public Command(String uri) {
		this.uri = uri;
	}
	
	public void start(Context context) {
		Intent intent = new Intent(context, WebService.class);
		intent.putExtra(WebService.COMMAND, this);
		context.startService(intent);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	protected void execute(Context context) {
		final long stime = System.currentTimeMillis();
		try {
			if (isNetworkAvailable(context)) {
				HttpResponse response = client.execute(getRequest());
				final int status = response.getStatusLine().getStatusCode();
				if (HttpStatus.SC_OK == status) {
					handleResults(context, response);
				} else {
					Log.d(Schema.TAG, "Request to uri returns code " + status);
				}
			}
			
		} catch (IOException e) {
			Log.d(Schema.TAG, "Failed to execute request to uri:" + uri);
		} finally {
			Log.d(Schema.TAG, 
					String.format("Elapsed time %s millis to %s", 
							System.currentTimeMillis() - stime, uri));
		}
	};
	
	protected abstract HttpUriRequest getRequest();
	
	protected abstract void handleResults(final Context context, final HttpResponse response);
	
	private  boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = 
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
	}

}
