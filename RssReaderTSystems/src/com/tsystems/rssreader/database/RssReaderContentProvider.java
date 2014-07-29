package com.tsystems.rssreader.database;

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

public class RssReaderContentProvider extends ContentProvider {

	private static final String DATABASE = "rssreader.db";

	private static final int VERSION = 1;

	private ContentResolver cr;

	public static final String AUTHORITY = "com.tsystems.rssreader";

	public static final Uri LINKS_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + Schema.Links.TABLE_NAME);

	public static final Uri FEEDS_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + Schema.Feeds.TABLE_NAME);

	public static final int LINKS_ITEM = 0;

	public static final int LINKS_DIR = 1;

	public static final int FEEDS_ITEM = 10;

	public static final int FEEDS_DIR = 11;

	private static final UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, LINKS_URI + "/#", LINKS_ITEM);
		uriMatcher.addURI(AUTHORITY, Schema.Links.TABLE_NAME, LINKS_DIR);
		uriMatcher.addURI(AUTHORITY, FEEDS_URI + "/#", FEEDS_ITEM);
		uriMatcher.addURI(AUTHORITY, Schema.Feeds.TABLE_NAME, FEEDS_DIR);
	}

	private DatabaseHelper db;

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case LINKS_ITEM:
			return "vnd.android.cursor.item/vnd." + AUTHORITY + ".item";
		case LINKS_DIR:
			return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".dir";
		case FEEDS_ITEM:
			return "vnd.android.cursor.item/vnd." + AUTHORITY + ".item";
		case FEEDS_DIR:
			return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".dir";
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String table = null;
		int match = uriMatcher.match(uri);
		switch (match) {
		case LINKS_DIR:
			table = Schema.Links.TABLE_NAME;
			break;
		case LINKS_ITEM:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		case FEEDS_DIR:
			table = Schema.Feeds.TABLE_NAME;
			break;
		case FEEDS_ITEM:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}
		long rowId = db.getWritableDatabase().insertWithOnConflict(table, null,
				values, SQLiteDatabase.CONFLICT_REPLACE);
		Uri newUri = ContentUris.withAppendedId(uri, rowId);
		cr.notifyChange(newUri, null);
		return newUri;

	}

	@Override
	public boolean onCreate() {
		cr = getContext().getContentResolver();
		db = new DatabaseHelper(getContext());
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		int match = uriMatcher.match(uri);
		switch (match) {
		case LINKS_DIR:
			builder.setTables(Schema.Links.TABLE_NAME);
			break;
		case LINKS_ITEM:
			builder.appendWhere("_id=" + uri.getLastPathSegment());
			break;
		case FEEDS_DIR:
			builder.setTables(Schema.Feeds.TABLE_NAME);
			break;
		case FEEDS_ITEM:
			builder.appendWhere("_id=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}
		Cursor cursor = builder.query(db.getReadableDatabase(), projection,
				selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(cr, uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String whereClause = null;
		String table = null;
		int match = uriMatcher.match(uri);
		switch (match) {
		case LINKS_ITEM:
			table = Schema.Links.TABLE_NAME;
			setNameAndClause(table, uri, selection, whereClause);
			break;
		case FEEDS_ITEM:
			table = Schema.Feeds.TABLE_NAME;
			setNameAndClause(table, uri, selection, whereClause);
			break;
		}
		int rows = db.getWritableDatabase().update(table, values, whereClause,
				selectionArgs);
		if (rows > 0) {
			cr.notifyChange(uri, null);
		}
		return rows;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = null;
		String whereClause = null;
		switch (uriMatcher.match(uri)) {
		case LINKS_ITEM:
			table = Schema.Links.TABLE_NAME;
			// TODO delete relatives feeds
			setNameAndClause(table, uri, selection, whereClause);
			break;
		case LINKS_DIR:
			table = Schema.Links.TABLE_NAME;
			whereClause = selection;
			// TODO delete relatives feeds
			break;
		case FEEDS_ITEM:
			table = Schema.Feeds.TABLE_NAME;
			setNameAndClause(table, uri, selection, whereClause);
			break;
		case FEEDS_DIR:
			table = Schema.Feeds.TABLE_NAME;
			break;
		}
		int rows = db.getWritableDatabase().delete(table, whereClause,
				selectionArgs);
		if (rows > 0) {
			cr.notifyChange(uri, null);
		}
		return rows;
	}

	private void setNameAndClause(String table, Uri uri, String selection,
			String whereClause) {
		String rowId = "_id=" + uri.getLastPathSegment();
		if (selection != null) {
			whereClause = selection + " AND " + rowId;
		} else {
			whereClause = rowId;
		}
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public static final String CREATE_LINKS_TABLE = "CREATE TABLE "
				+ Schema.Links.TABLE_NAME + " (" + Schema.Links._ID
				+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ Schema.Links.LINK + " TEXT, "
				+ Schema.Links.TITLE + " TEXT);";

		public static final String CREATE_FEEDS_TABLE = "CREATE TABLE "
				+ Schema.Feeds.TABLE_NAME + " (" + Schema.Feeds._ID
				+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ Schema.Feeds.GUID + " TEXT, " 
				+ Schema.Feeds.LINK + " TEXT, " 
				+ Schema.Feeds.TITLE + " TEXT, " 
				+ Schema.Feeds.DESC + " TEXT, " 
				+ Schema.Feeds.PUB_DATE + " TEXT, "
				+ Schema.Feeds.VIEWED + " TEXT);";

		public DatabaseHelper(Context context) {
			super(context, DATABASE, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_LINKS_TABLE);
			db.execSQL(CREATE_FEEDS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			dropTable(Schema.Links.TABLE_NAME, db);
			dropTable(Schema.Feeds.TABLE_NAME, db);
			db.execSQL(CREATE_LINKS_TABLE);
			db.execSQL(CREATE_FEEDS_TABLE);
		}

		private void dropTable(final String name, final SQLiteDatabase db) {
			db.execSQL("DROP TABLE IF EXISTS " + name);
		}

	}

}
