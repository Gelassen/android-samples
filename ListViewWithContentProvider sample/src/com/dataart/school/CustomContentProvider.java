package com.dataart.school;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.dataart.school.Schema.Stores;

public class CustomContentProvider extends ContentProvider {

    private static final String DATABASE = "ListViewAndContentProviderSample.db";

    public static final String AUTHORITY = "com.dataart.school";

    public static final Uri STORES_URI = Uri.parse("content://" + AUTHORITY + "/" + Schema.Stores.TABLE_NAME);

    private ContentResolver cr;

    private DatabaseHelper db;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int STORES_DIR = 0;

    private static final int STORES_ITEM = 1;

    static {
	uriMatcher.addURI(AUTHORITY, Schema.Stores.TABLE_NAME, STORES_DIR);
	uriMatcher.addURI(AUTHORITY, Schema.Stores.TABLE_NAME + "/#", STORES_ITEM);
    }

    @Override
    public boolean onCreate() {
	cr = getContext().getContentResolver();
	db = new DatabaseHelper(getContext());
	return db == null ? false : true;
    }

    @Override
    public String getType(Uri uri) {
	switch (uriMatcher.match(uri)) {
	case STORES_DIR:
	    return "vnd.android.cursor.dir/" + "vnd." + AUTHORITY + "." + Schema.Stores.TABLE_NAME;
	case STORES_ITEM:
	    return "vnd.android.cursor.item/" + "vnd." + AUTHORITY + "." + Schema.Stores.TABLE_NAME;
	default:
	    throw new IllegalArgumentException("Uri is not supported");
	}
    }

    @Override
    public Cursor query(Uri uri, String[] proejction, String selection, String[] selectionArgs, String sortOrder) {
	SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	switch (uriMatcher.match(uri)) {
	case STORES_DIR:
	    builder.setTables(Stores.TABLE_NAME);
	    break;

	case STORES_ITEM:
	    builder.setTables(Stores.TABLE_NAME);
	    selection = Schema.Stores.ID + "=" + uri.getLastPathSegment();
	    break;

	default:
	    Log.e(Schema.TAG, "Unsupported uri " + uri.toString());
	    break;
	}
	Cursor cursor = builder.query(db.getReadableDatabase(), proejction, selection, selectionArgs, null, null,
		sortOrder);
	cursor.setNotificationUri(cr, uri);
	return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
	String table = null;
	switch (uriMatcher.match(uri)) {
	case STORES_DIR:
	    table = Stores.TABLE_NAME;
	    break;

	default:
	    Log.e(Schema.TAG, "Unsupported uri " + uri.toString());
	    break;
	}
	long itemId = db.getWritableDatabase().insertWithOnConflict(table, null, values,
		SQLiteDatabase.CONFLICT_REPLACE);
	Uri newUri = ContentUris.withAppendedId(uri, itemId);
	cr.notifyChange(newUri, null);
	return newUri;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
	// TODO Auto-generated method stub
	return 0;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

	private static final String CREATE_STORES = "CREATE TABLE " + Schema.Stores.TABLE_NAME + " ("
		+ Schema.Stores._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + Schema.Stores.ID
		+ " INTEGER UNIQUE ON CONFLICT REPLACE, " + Schema.Stores.STORE_NAME + " TEXT, "
		+ Schema.Stores.ADDRESS + " TEXT, " + Schema.Stores.PHONE + " TEXT, " + Schema.Stores.LATITUDE
		+ " TEXT, " + Schema.Stores.LONGITUDE + " TEXT);";

	public DatabaseHelper(Context context) {
	    super(context, DATABASE, null, Schema.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL(CREATE_STORES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    dropTable(db, Schema.Stores.TABLE_NAME);
	    onCreate(db);
	}

	private void dropTable(SQLiteDatabase db, String table) {
	    db.execSQL("DROP TABLE IF EXISTS " + table);
	}

    }
}
