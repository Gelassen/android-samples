package com.dataart.school.appinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dataart.school.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoresMapActivity extends FragmentActivity {

	private static final double DEFAULT_LON = 144.96298;
	
	private static final double DEFAULT_LAT = -37.81319;
	
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.map_layout);
		
		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setTrafficEnabled(true);
		showMarker(DEFAULT_LAT, DEFAULT_LON);
//		Intent intent = getIntent();
//		Bundle extras = intent.getExtras();
//		if (extras != null) {
//			final double lon = extras.getDouble("LONGITUDE");
//			final double lat = extras.getDouble("LATITUDE");
//			showMarker(DEFAULT_LAT, DEFAULT_LON);
//		}
		
	}

	private void showMarker(double lat, double lon) {
		LatLng pos = new LatLng(lat, lon);
		BitmapDescriptor picDescriptor = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		MarkerOptions marker = new MarkerOptions()
			.position(pos)
			.icon(picDescriptor);
		map.addMarker(marker);
		
		
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-37.81319, 144.96298), 10f);
		map.moveCamera(cameraUpdate);
		
//		CameraUpdate zoom = CameraUpdateFactory.zoomTo(10f);
//		map.animateCamera(zoom);
		LatLng southwest = new LatLng(-27.470894, 153.023291);
		LatLng northeast  = pos;
		LatLngBounds bounds = new LatLngBounds(northeast, southwest);
		cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80, 80, 0);
		map.moveCamera(cameraUpdate);
		
	}
	
	

}
