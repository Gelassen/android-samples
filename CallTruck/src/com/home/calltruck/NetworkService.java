package com.home.calltruck;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class NetworkService extends IntentService{

	public static final String FAKE_REQUEST = "FAKE_REQUEST";
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	
	private static final AndroidHttpClient client = AndroidHttpClient.newInstance(CallTruck.TAG);
	
	public NetworkService() {
		super(CallTruck.TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		if (FAKE_REQUEST.equals(action)) {
			if (internetConnectionAvailable(this)) {
				CreateOrderCommand command = (CreateOrderCommand) 
						intent.getSerializableExtra(FAKE_REQUEST);
				execute(command);
			} else {
				Intent msgToActivity = new Intent(FAKE_REQUEST);
				String msg = "ќтсутствует соединение с сервером. ¬ы разерешили доступ через wi-fi или мобильные сети?";
				msgToActivity.putExtra(FAKE_REQUEST, msg);
				sendBroadcast(msgToActivity);
			}
		}
	}

	private void execute(CreateOrderCommand command) {
		try {
			Intent msgToActivity = new Intent(FAKE_REQUEST);
			
			HttpPost request = command.createFakeRequest();
			HttpResponse response = client.execute(request);
			final int code = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == code) {
				HttpEntity entity = response.getEntity();
				String res = EntityUtils.toString(entity);
				msgToActivity.putExtra(FAKE_REQUEST, res);
				sendBroadcast(msgToActivity);
				Log.d(CallTruck.TAG, res);
			} else {
				Log.d(CallTruck.TAG, "Error on the server with code " + code);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					Header[] headers = response.getAllHeaders();
					for (Header header : headers) {
						Log.e(CallTruck.TAG, "Header " + header.getName() + " " + header.getValue());
					}
					String res = EntityUtils.toString(entity);
					msgToActivity.putExtra(FAKE_REQUEST, res);
					sendBroadcast(msgToActivity);
					Log.e(CallTruck.TAG, "Erro message " + res);
				}
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(CallTruck.TAG, "Failed to encode request", e);
		} catch (IOException e) {
			Log.e(CallTruck.TAG, "Failed to get response", e);
		}
	}

	
    private boolean internetConnectionAvailable(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
	
//	private void readError(InputStream is) {
//		BufferedInputStream reader = new BufferedInputStream(is);
//		reader.read(buffer)
//	}

}
