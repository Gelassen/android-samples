package com.example.interview.network.commands;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.InterviewApplication;
import com.example.interview.convertors.PageWithVideosToStorageConverter;
import com.example.interview.entity.NetworkLibrary;
import com.example.interview.model.api.PageWithVideos;
import com.example.interview.network.Command;
import com.example.interview.network.Status;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The intent of this class is get first page with videos
 *
 * Created by John on 10/3/2016.
 */
public class GetInitialVideoPageCommand extends Command {

    protected Status status;

    public GetInitialVideoPageCommand() {
        notify = true;
        status = new Status();
    }

    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        try {
            if (isNetworkAvailable()) {
                NetworkLibrary networkLibrary = ((InterviewApplication) context.getApplicationContext()).getNetworkLibrary();
                Call<PageWithVideos> pageWithVideos = networkLibrary.getApi().getInitialPageWithVideos();
                Response<PageWithVideos> response = pageWithVideos.execute();
                if (response.isSuccessful()) {
                    save(context, response.body());
                } else {
                    status.add(Status.FAILED_TO_EXECUTE_REQUEST);
                }
            } else {
                Log.e(App.TAG, "Network is not available");
                status.add(Status.FAILED_NETWORK);
            }
        } catch (Exception ex) {
            Log.e(App.TAG, "Failed to execute request", ex);
            status.add(Status.FAILED_TO_EXECUTE_REQUEST);
        }

        notifyListeners(status);
    }

    private boolean save(Context context, PageWithVideos pageWithVideos) {
        PageWithVideosToStorageConverter converter = new PageWithVideosToStorageConverter(context);
        converter.init(pageWithVideos.getData());
        return converter.convert();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // no op
    }

    protected static final Parcelable.Creator<GetInitialVideoPageCommand> CREATOR = new Creator<GetInitialVideoPageCommand>() {
        @Override
        public GetInitialVideoPageCommand createFromParcel(Parcel parcel) {
            return new GetInitialVideoPageCommand();
        }

        @Override
        public GetInitialVideoPageCommand[] newArray(int i) {
            return new GetInitialVideoPageCommand[0];
        }
    };

}
