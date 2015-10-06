package com.home.traveller.storage;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.home.traveller.App;

import java.util.HashMap;

public class AppContentProvider extends ContentProvider {

    private ContentResolver cr;
    private AppOpenHelper db;

    @Override
    public boolean onCreate() {
        cr = getContext().getContentResolver();
        db = new AppOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int matchId = matcher.match(uri);
        final String table = getTable(matchId);

        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(table);
        String groupBy = null;
        switch (matchId) {
            case MATCH_CARDS:
                // do what you want
                break;
            default:
                groupBy = null;
        }

        final Cursor cursor = builder.query(db.getReadableDatabase(),projection, selection, selectionArgs, groupBy, null, sortOrder);
        cursor.setNotificationUri(cr, uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int matchId = matcher.match(uri);
        final String table = getTable(matchId);
        final long id = db.getWritableDatabase().insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (id > -1 && isNotifyEnabled(uri)) {
            final int insertedRows = 1;
            notifyChange(uri, insertedRows);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int matchId = matcher.match(uri);
        final String table = getTable(matchId);
        final int rows = db.getWritableDatabase().delete(table, selection, selectionArgs);
        notifyChange(uri, rows);
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int matchId = matcher.match(uri);
        final String table = getTable(matchId);
        final int rows = db.getWritableDatabase().update(table, values, selection, selectionArgs);
        notifyChange(uri, rows);
        return rows;
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {
        int numInserted = 0;
        if (values.length == 0) return  numInserted;

        String table = getTable(uri);

        SQLiteDatabase database = db.getWritableDatabase();
        try {
            database.beginTransaction();
            int length = values.length;
            for (ContentValues value : values) {
                database.insertWithOnConflict(table, null, value, SQLiteDatabase.CONFLICT_REPLACE);
            }

            numInserted = length;
            database.setTransactionSuccessful();
            if (isNotifyEnabled(uri)) {
                notifyChange(uri, numInserted);
            }
        } catch (Exception ex) {
            Log.e(App.TAG, "Failed to insert bunch rows: " + table, ex);
        } finally {
            database.endTransaction();
        }

        return numInserted;
    }

    private boolean isNotifyEnabled(final Uri uri) {
        return !Contract._FRAGMENT_NO_NOTIFY.equals(uri.getFragment());
    }

    private void notifyChange(final Uri uri, final int rows) {
        if (rows > 0) {
            cr.notifyChange(uri, null);
        }
    }

    private String getTable(final Uri uri) {
        return getTable(matcher.match(uri));
    }

    private String getTable(final int matchId) {
        String table = tables.get(matchId);
        if (table == null) {
            throw new IllegalArgumentException("Tables doesn't exist for code " + matchId);
        }
        return table;
    }

    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static SparseArray<String> tables = new SparseArray<>();

    private static void match(Class<? extends Contract.SqlEntity> table, int code) {
        if (tables.get(code) != null ) {
            throw new IllegalArgumentException("Duplicate code for " + table.getSimpleName());
        }

        String tableName = Contract.Join.class.isAssignableFrom(table)
                ? Contract.Join.getJoinTable((Class<Contract.Join>) table) : Contract.getTableName(table);

        String path = table.getSimpleName();
        matcher.addURI(Contract.AUTHORITY, path, code);
        tables.put(code, tableName);
    }

    private final static int MATCH_CARDS = 0x00000001;

    static {
        match(Contract.CardsTable.class, MATCH_CARDS);
    }

}
