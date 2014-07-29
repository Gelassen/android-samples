package com.example.home.kazakov.camera_training;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final int SOME_REQUEST_CODE = 0;

	private static final int ACTION_TAKE_VIDEO = 1;
	
	private String imagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.shot:
			try {
				dispatchTakePictureIntent(SOME_REQUEST_CODE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case R.id.record:
			   Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			    startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);

			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SOME_REQUEST_CODE) {
			galleryAddPic();
			scaleImage();
		} else if (requestCode == ACTION_TAKE_VIDEO) {
			Log.e("Uro to video", data.getData().toString());
		}
	}
	
	public boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(
				intent, 
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	private void dispatchTakePictureIntent(int actionCode) throws IOException {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPath()));
	    if (isAvailable(takePictureIntent)) {
	    	startActivityForResult(takePictureIntent, actionCode);
	    }
	}
	
	private File getDir() throws IOException {
		File file = new File(
				Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_PICTURES
					), 
				"pictures");
		if (!file.mkdir()) {
			Log.d("kazakov", "Failed to create dir");
		}
		return file;
	}

	private File getPath() throws IOException {
		String timeStamp = 
		        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = timeStamp + "_";

		final File dir = getDir();
		boolean ttt = false;
		if (!dir.exists())
			ttt = dir.mkdirs();
		Log.e("asdasdasd", ttt ? "true" : " false");
		File file = File.createTempFile(imageFileName, "jpg", dir);
		imagePath = file.getAbsolutePath();
		return file;
	}
	
	private void galleryAddPic() {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(new File(imagePath));
		intent.setData(uri);
		sendBroadcast(intent);
	}
	
	private void scaleImage() {
		ImageView imView = (ImageView) findViewById(R.id.image);
		final int targetH = 160;imView.getHeight();
		final int targetW = 80;imView.getWidth();
		
		BitmapFactory.Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);
		final int photoH = options.outHeight;
		final int photoW = options.outWidth;
		
		options.inJustDecodeBounds = false;
		options.inSampleSize = Math.min(photoW/targetW, photoH/targetH);
		options.inPurgeable = true;
		
		Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
		imView.setImageBitmap(bm);
	}

	private boolean isAvailable(Intent intent) {
		PackageManager manager = getPackageManager();
		List<ResolveInfo> list = 
				manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
}
