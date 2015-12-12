package com.home.traveller.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.home.traveller.storage.Contract;

/**
 * Created by dmitry.kazakov on 12/11/2015.
 */
public class TestUtil {

    public static void generateTestData(Context context) {
        ContentResolver cr = context.getContentResolver();

        ContentValues values = generateCardsValues("/sdcard", "Comment 1", "Tag 1");
        cr.insert(Contract.contentUri(Contract.CardsTable.class), values);

        values = generateCardsValues("/pictures", "Comment 2", "Tag 2");
        cr.insert(Contract.contentUri(Contract.CardsTable.class), values);

        values = generateCardsValues("/download", "Comment 3", "Tag 3");
        cr.insert(Contract.contentUri(Contract.CardsTable.class), values);

        values = generateCardsValues("/images", "Comment 4", "Tag 4");
        cr.insert(Contract.contentUri(Contract.CardsTable.class), values);

        // association
        values = generateAssociationValues("Comment 1");
        cr.insert(Contract.contentUri(Contract.AssociationSample.class), values);

        values = generateAssociationValues("Comment 2");
        cr.insert(Contract.contentUri(Contract.AssociationSample.class), values);

        // tag
        values = generateTagValues("Comment 1", "fake");
        cr.insert(Contract.contentUri(Contract.TagSample.class), values);
    }

    private static ContentValues generateCardsValues(String path, String comment, String tag) {
        ContentValues values = new ContentValues();
        values.put(Contract.CardsTable.IMAGE_PATH, path);
        values.put(Contract.CardsTable.COMMENT, comment);
        values.put(Contract.CardsTable.TAG, tag);
        return values;
    }

    private static ContentValues generateAssociationValues(String association) {
        ContentValues values = new ContentValues();
        values.put(Contract.AssociationSample.ASSOCIATION, association);
        return values;
    }

    public static ContentValues generateTagValues(String tag, String fakeData) {
        ContentValues values = new ContentValues();
        values.put(Contract.TagSample.TAG, tag);
//        values.put(Contract.TagSample.TEST, fakeData);
        return values;
    }

}
