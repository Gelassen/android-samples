package com.home.xing.network.parsers;

import android.content.ContentValues;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.home.xing.Xing;
import com.home.xing.network.model.Repo;
import com.home.xing.storage.Contract;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public class RepoParser extends Parser {

    private LinkedList<ContentValues> values;

    public RepoParser(LinkedList<ContentValues> values) {
        this.values = values;
    }

    @Override
    public void parse(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            ContentValues row = new ContentValues(4);
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(Repo.NAME)) {
                    String tmp = reader.nextString();
                    Log.d(Xing.TAG, tmp);
                    row.put(Contract.RepoTable.REPO_NAME, tmp);

                } else if (name.equals(Repo.OWNER) && reader.peek() != JsonToken.NULL) {
                    reader.beginObject();
                    parseOwner(reader, row);
                    reader.endObject();

                } else if (name.equals(Repo.DESCRIPTION)) {
                    row.put(Contract.RepoTable.DESCRIPTION, reader.nextString());

                } else if (name.equals(Repo.FORK) && reader.peek() != JsonToken.NULL) {
                    row.put(Contract.RepoTable.FORK, reader.nextBoolean() ? 1 : 0);

                } else if (name.equals(Repo.ID)) {
                    row.put(Contract.RepoTable.ID, reader.nextInt());

                } else if (name.equals(Repo.REPO_URL)) {
                    row.put(Contract.RepoTable.REPO_URL, reader.nextString());

                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            row.put(Contract.RepoTable.CACHED, System.currentTimeMillis());
            values.add(row);
        }
        reader.endArray();
    }

    private void parseOwner(JsonReader reader, ContentValues row) throws IOException {
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if (name.equals(Repo.OWNER_LOGIN)) {
                row.put(Contract.RepoTable.OWNER, reader.nextString());
            } else if (name.equals(Repo.OWNER_URL)) {
                row.put(Contract.RepoTable.OWNER_URL, reader.nextString());

            } else {
                reader.skipValue();
            }
        }
    }
}
