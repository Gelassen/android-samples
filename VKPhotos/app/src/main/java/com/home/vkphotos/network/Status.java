package com.home.vkphotos.network;


import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Status {

    public static final int FAILED_NETWORK = 0;
    public static final int FAILED_TO_EXECUTE_REQUEST = 1;

    private static final String NAMESPACE = Status.class.getName();

    private static final String CMD_STATUS_MSG = NAMESPACE.concat(".CMD_STATUS_MSG");
    private static final String CMD_STATUS_CODE = NAMESPACE.concat(".CMD_STATUS_CODE");
    private static final String CMD_PAYLOAD = NAMESPACE.concat(".CMD_PAYLOAD");

    private Bundle status;

    public Status() {
        this.status = new Bundle();
    }

    private Status(Bundle extras) {
        this.status = extras;
    }

    public static Status from(Bundle bundle) {
        return new Status(bundle);
    }

    public Status add(int statusCode) {
        status.putInt(CMD_STATUS_CODE, statusCode);
        return this;
    }

    public Status add(String statusMessage) {
        status.putString(CMD_STATUS_MSG, statusMessage);
        return this;
    }

    public Status add(Bundle extras) {
        status.putAll(extras);
        return this;
    }

    public int getStatusCode() {
        return status.getInt(CMD_STATUS_CODE);
    }

    public void setPayload(ArrayList<? extends Parcelable> payload) {
        status.putParcelableArrayList(CMD_PAYLOAD, payload);
    }

    public ArrayList<? extends Parcelable> getPayload() {
        return status.getParcelableArrayList(CMD_PAYLOAD);
    }

    public boolean isSuccess() {
        // extend this if you expect others status code as valid
        // HttpStatus.SC_OK
        return 200 == getStatusCode();
    }

    public Object getExtra(final String extraField) {
        return status.get(extraField);
    }

    /*package*/ Bundle getBundle() {
        return status;
    }
}