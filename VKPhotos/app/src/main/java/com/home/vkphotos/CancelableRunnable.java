package com.home.vkphotos;


import java.util.concurrent.atomic.AtomicBoolean;

public class CancelableRunnable implements Runnable {

    protected volatile AtomicBoolean lock = new AtomicBoolean(false);

    @Override
    public void run() {
        lock.set(true);
    }

    public void cancel() {
        lock.set(false);
    }
}
