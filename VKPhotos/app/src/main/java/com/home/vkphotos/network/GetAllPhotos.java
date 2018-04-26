package com.home.vkphotos.network;


import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.home.vkphotos.App;
import com.home.vkphotos.R;
import com.home.vkphotos.photos.preview.JsonToResponseConverter;
import com.home.vkphotos.photos.preview.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

public class GetAllPhotos extends HttpCommand {

    private static final int PAGE = 20;

    private String ownerId;
    private int offset;
    private String accessToken;

    public GetAllPhotos(String ownerId, int offset, String accessToken) {
        this.ownerId = ownerId;
        this.offset = offset;
        this.accessToken = accessToken;
    }

    protected GetAllPhotos(Parcel in) {
        ownerId = in.readString();
        offset = in.readInt();
        accessToken = in.readString();
    }

    @Override
    protected void createRequest(Context context) {
        Uri.Builder builder = new Uri.Builder()
                .encodedPath(context.getString(R.string.url))
                .appendPath("photos.getAll")
                .appendQueryParameter("owner_id", String.valueOf(ownerId))
                .appendQueryParameter("offset", String.valueOf(offset))
                .appendQueryParameter("count", String.valueOf(PAGE))
                .appendQueryParameter("access_token", accessToken)
                .appendQueryParameter("v", String.valueOf(5.74));

        request = new Request.Builder()
                .url(builder.build().toString())
                .build();
    }

    // TODO handle null list there

    @Override
    protected void processRequest(okhttp3.Response response) {
        super.processRequest(response);
        try {
            JSONObject jObject = new JSONObject(response.body().string());

            Log.e(App.TAG, "Response for page: " + offset + " \n " + jObject.toString());

            Response resp = new JsonToResponseConverter()
                    .convert(jObject);

            status.setPayload(new ArrayList<Parcelable>(resp.getItems()));
        } catch (JSONException e) {
            Log.e(App.TAG, "Failed to obtain data", e);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to obtain data", e);
        } catch (Exception e) {
            Log.e(App.TAG, "Failed to obtain data", e);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ownerId);
        parcel.writeInt(offset);
        parcel.writeString(accessToken);
    }

    public static final Creator<GetAllPhotos> CREATOR = new Creator<GetAllPhotos>() {
        @Override
        public GetAllPhotos createFromParcel(Parcel in) {
            return new GetAllPhotos(in);
        }

        @Override
        public GetAllPhotos[] newArray(int size) {
            return new GetAllPhotos[size];
        }
    };
}
