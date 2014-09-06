package com.home.xing.network.commands;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Log;

import com.home.xing.Params;
import com.home.xing.R;
import com.home.xing.Xing;
import com.home.xing.network.HttpCommand;
import com.home.xing.network.parsers.RepoParser;
import com.home.xing.storage.Contract;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public class GetReposCommand extends HttpCommand {

    private int page;
    private int perPage;

    public GetReposCommand(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
    }

    @Override
    protected void createRequest() {
        Uri.Builder builder = Uri.parse(context.getString(R.string.url_github)).buildUpon();
        builder.appendEncodedPath(context.getString(R.string.url_github_xing_repos));
        builder.appendQueryParameter("page", String.valueOf(page));
        builder.appendQueryParameter("per_page", String.valueOf(perPage));

        Log.d(Xing.TAG, String.format("Start repo command for page %s with %s per page", page, perPage));

        httpUriRequest = new HttpGet(builder.toString());
        httpUriRequest.addHeader(Headers.ACCEPT, Headers.ACCEPT_DEFAULT_VALUE);
    }

    @Override
    protected void processRequest(HttpResponse response) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent()));
            LinkedList<ContentValues> repos = new LinkedList<ContentValues>();
            new RepoParser(repos).parse(reader);

            ContentValues[] eventsValues = new ContentValues[repos.size()];
            repos.toArray(eventsValues);
            Uri reposUri = Contract.contentUri(Contract.RepoTable.class);
            context.getContentResolver().bulkInsert(reposUri, eventsValues);
        } catch (Exception e) {
            Log.e(Xing.TAG, "Failed to obtain content", e);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(perPage);
    }

    public final static Parcelable.Creator<GetReposCommand> CREATOR = new Parcelable.Creator<GetReposCommand>() {

        @Override
        public GetReposCommand[] newArray(int size) {
            return new GetReposCommand[size];
        }

        @Override
        public GetReposCommand createFromParcel(Parcel source) {
            return new GetReposCommand(
                    source.readInt(),
                    source.readInt()
            );
        }
    };
}
