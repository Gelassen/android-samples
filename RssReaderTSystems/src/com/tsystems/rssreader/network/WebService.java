package com.tsystems.rssreader.network;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.tsystems.rssreader.database.Schema;

public class WebService extends Service {

	public static final String COMMAND = "command";
	
	private WakeLock wakeLock;
	
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 6, 2, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WebService.class.getName());
		wakeLock.acquire();
	}

	@Override
	public void onDestroy() {
		pool.shutdown();
		wakeLock.release();
		Log.d(Schema.TAG, "WebService is stoped");
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Command command = intent.getParcelableExtra(COMMAND);
		pool.submit(new Runnable() {
			
			@Override
			public void run() {
				if (null != command) {
					command.execute(getApplicationContext());
				} else {
					Log.d(Schema.TAG, "Command is not set in intent");
				}
			}
		});
		return START_NOT_STICKY;
	}

}
