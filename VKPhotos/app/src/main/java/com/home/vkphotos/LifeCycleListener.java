package com.home.vkphotos;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleListener {

    private List<LifeCycleCallbacks> callbacks;

    public LifeCycleListener() {
        callbacks = new ArrayList<>();
    }

    public void notifyOnLowMemory() {
        for (LifeCycleCallbacks callbacks : callbacks) {
            callbacks.onLowMemoryCall();
        }
    }

    public void subscribe(LifeCycleCallbacks listener) {
        callbacks.add(listener);
    }

    public void unsubscribe(LifeCycleCallbacks listener) {
        callbacks.remove(listener);
    }

    public interface LifeCycleCallbacks {
        void onLowMemoryCall();
    }
}
