package com.home.traveller.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.home.traveller.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry.kazakov on 10/2/2015.
 */
public class Card {
    private String path;
    private String tag = "";
    private String desc = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ContentValues asContentValues() {
        ContentValues values = new ContentValues(3);
        values.put(Contract.CardsTable.IMAGE_PATH, path);
        values.put(Contract.CardsTable.COMMENT, desc);
        values.put(Contract.CardsTable.TAG, tag);
        return values;
    }

    public static Card fromCursorAsItem(Cursor cursor) {
        Card card = new Card();
        card.setTag(cursor.getString(cursor.getColumnIndex(Contract.CardsTable.TAG)));
        card.setPath(cursor.getString(cursor.getColumnIndex(Contract.CardsTable.IMAGE_PATH)));
        card.setDesc(cursor.getString(cursor.getColumnIndex(Contract.CardsTable.COMMENT)));
        return card;
    }

    public static List<Card> fromCursorAsList(Cursor cursor) {
        List<Card> cards = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            cards.add(Card.fromCursorAsItem(cursor));
        }
        return cards;
    }

    public enum Tag {
        SELECTED, UNSELECTED
    }

}
