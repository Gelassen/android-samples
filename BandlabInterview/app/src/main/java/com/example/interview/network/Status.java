package com.example.interview.network;


import android.os.Bundle;

public class Status {

    public static final int FAILED_NETWORK = 0;
    public static final int FAILED_TO_EXECUTE_REQUEST = 1;

    private static final int SUCCESS = 200;

    private static final String NAMESPACE = Status.class.getName();

    private static final String CMD_STATUS_MSG = NAMESPACE.concat(".CMD_STATUS_MSG");
    private static final String CMD_STATUS_CODE = NAMESPACE.concat(".CMD_STATUS_CODE");

    private Bundle status;

    public Status() {
        this.status = new Bundle();
        add(SUCCESS);
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

    public boolean isSuccess() {
        return getStatusCode() == SUCCESS;
    }

    public Object getExtra(final String extraField) {
        return status.get(extraField);
    }

    /*package*/ Bundle getBundle() {
        return status;
    }
}
