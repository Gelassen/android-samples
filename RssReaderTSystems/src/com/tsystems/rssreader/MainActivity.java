package com.tsystems.rssreader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tsystems.rssreader.database.RssReaderContentProvider;
import com.tsystems.rssreader.database.Schema;
import com.tsystems.rssreader.network.LoadFeedCommand;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case R.id.menu_add_link:
			new LinksDialog(LinksDialog.ADD_LINK)
				.show(getSupportFragmentManager(), Schema.TAG);
			break;
			
		case R.id.menu_delete_link:
			new LinksDialog(LinksDialog.DELETE_LINK)
				.show(getSupportFragmentManager(), Schema.TAG);
			break;
			
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
			
		case R.id.menu_cache:
			CustomQueryHandler handler = new CustomQueryHandler(getContentResolver());
			handler.startQuery(CustomQueryHandler.GET_ALL_LINKS, 
					null, 
					RssReaderContentProvider.LINKS_URI, 
					new String[] { Schema.Links.LINK }, 
					null, null, null);
			break;
		}
    	return true;
	}

	public class LinksDialog extends DialogFragment {
    	
		private static final int ADD_LINK = 0;
		
		private static final int DELETE_LINK = 1;
		
		private final int type;
		
		public LinksDialog(final int type) {
			this.type = type;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Context context = getActivity();
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.dialog_layout, null);
			
			final EditText title = (EditText) view.findViewById(R.id.link_title);
			final EditText link = (EditText) view.findViewById(R.id.link_url);
			link.setVisibility(type == DELETE_LINK ? View.GONE : View.VISIBLE);

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(R.string.links_title);
			builder.setView(view);
			builder.setPositiveButton(type == DELETE_LINK ? R.string.delete_link : R.string.add_link, 
				new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// all actions with base would be in a separate thread 
					CustomQueryHandler asyncQueryHandler = new CustomQueryHandler(getContentResolver());
					switch (type) {
					case ADD_LINK:
						ContentValues values = new ContentValues();
						values.put(Schema.Links.TITLE, title.getText().toString());
						values.put(Schema.Links.LINK, link.getText().toString());
						
						asyncQueryHandler.startInsert(CustomQueryHandler.INSERT_LINK, 
								null, 
								RssReaderContentProvider.LINKS_URI, 
								values);
						break;

					case DELETE_LINK:
						final String uri = link.getText().toString();
						asyncQueryHandler.startDelete(CustomQueryHandler.DELETE_LINK, 
								null, 
								RssReaderContentProvider.LINKS_URI, 
								Schema.Links.LINK + "=?", 
								new String[] { uri });
						break;
					}


					
					LoadFeedCommand cmd = new LoadFeedCommand(link.getText().toString());
					cmd.start(context);
				}
			});
			builder.setNegativeButton(R.string.cancel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			
			return builder.create();
		}
    	
    	
    }
	
	/*package*/ class CustomQueryHandler extends AsyncQueryHandler {

		public static final int INSERT_LINK = 0;
		
		public static final int DELETE_LINK = 1;
		
		public static final int GET_ALL_LINKS = 2;
		
		public CustomQueryHandler(ContentResolver cr) {
			super(cr);
		}
		
		
		@Override
		protected void onInsertComplete(int token, Object cookie, Uri uri) {
			super.onInsertComplete(token, cookie, uri);
			switch (token) {
			case INSERT_LINK:
				Toast.makeText(getApplicationContext(), 
						"Link added", Toast.LENGTH_SHORT).show();
				break;

			case DELETE_LINK:
				Toast.makeText(getApplicationContext(), 
						"Link deleted", Toast.LENGTH_SHORT).show();
				break;
			}
		}



		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			super.onQueryComplete(token, cookie, cursor);
			switch (token) {
			case GET_ALL_LINKS:
				if (cursor.moveToFirst()) {
					final int linkIdx = cursor.getColumnIndex(Schema.Links.LINK);
					String link;
					do {
						link = cursor.getString(linkIdx);
						LoadFeedCommand command = new LoadFeedCommand(link);
						command.start(getApplicationContext());
					} while (cursor.moveToNext()); 
				}
				break;
			}
		}
		
	}
}
