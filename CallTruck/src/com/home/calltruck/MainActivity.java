package com.home.calltruck;

import java.io.IOException;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements LocationListener{

	private ActionBar actionBar;
	
	private LocationManager manager;
	
	private boolean addressFound;
	
	private Location location;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        addressFound = false;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_call:
			String phone = "tel: 8-800-333-04-52";
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
			startActivity(intent);
			break;
			
		case R.id.menu_send:
			getData();
			break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (enabled) {
        	manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        	startActivity(intent);
        }
        
        registerReceiver(receiver, new IntentFilter(NetworkService.FAKE_REQUEST));
	}

	@Override
	protected void onStop() {
		super.onStop();
		manager.removeUpdates(this);
		unregisterReceiver(receiver);
	}

	private boolean valid(String phone, String from) {
		String regexStr = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
		boolean validData = false;
		if (null != phone && !TextUtils.isEmpty(phone) 
				&& android.util.Patterns.PHONE.matcher(phone).matches()
				&& null != from && !TextUtils.isEmpty(from)) {
			validData = true;
		}
		return validData;
	}
	
    private void getData() {
    	String name = ((EditText) findViewById(R.id.name)).getText().toString();
    	String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
    	String from = ((EditText) findViewById(R.id.pickup_place)).getText().toString();
    	String where = ((EditText) findViewById(R.id.deliver_place)).getText().toString();
//    	String date = ((TextView) findViewById(R.id.date_label)).getText().toString();
//    	String time = ((TextView) findViewById(R.id.time_label)).getText().toString();
    	String comment = ((EditText) findViewById(R.id.comment)).getText().toString();
    	
    	if (valid(phone, from)) {
    		// send to the server
            Intent intent = new Intent(this, NetworkService.class);
            intent.setAction(NetworkService.FAKE_REQUEST);
            CreateOrderCommand command = new CreateOrderCommand(name, phone, 
            		addressFound ? location.getLatitude() : 0d, 
            		addressFound ? location.getLongitude() : 0d, 
            		from, 0d, 0d, where, comment);
            intent.putExtra(NetworkService.FAKE_REQUEST, command);
            startService(intent);
    	} else {
    		String msg = "Поля адрес и номер телефона обязательны. Пожалуйста корректно заполните их.";
    		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    	}
    }

	@Override
	public void onLocationChanged(Location location) {
		Geocoder geocoder = new Geocoder(this);
		try {
			List<Address> addresses = geocoder.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);
			if (!addresses.isEmpty()) {
				addressFound = true;
				manager.removeUpdates(this);
				this.location = location;
				String address = addresses.get(0).getAddressLine(0);
				((EditText) findViewById(R.id.pickup_place)).setText(address);
			}
		} catch (IOException e) {
			Log.e(CallTruck.TAG, "Failed to obtain address", e);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// no op
	}

	@Override
	public void onProviderEnabled(String provider) {
		// no op
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// no op
		
	}
	
	/*packahe*/ void showServerResponse(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
    
	BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			//we have only one type of broadcast so we can skip assertion 
			String msg = intent.getStringExtra(NetworkService.FAKE_REQUEST);
			showServerResponse(msg);
		}
	};
	
}
