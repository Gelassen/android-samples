package com.home.myapplication;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.LinkedList;

/**
 * Created by dmitry.kazakov on 7/16/2016.
 */
public class Animation implements Runnable {

    private static final long FRAMES = 1;
    private static final long ANIMATION_STEP = 1000 / FRAMES;

//    private LinkedList<Point> path = new LinkedList<>();

    private LinkedList<Float> firstRadisuList = new LinkedList<>();
    private LinkedList<Float> secondRadisuList = new LinkedList<>();

    private AnimConfig config;
    private Handler handler;

    private Callbacks listener;

    public Animation(AnimConfig config) {
        this.config = config;
        this.handler = new Handler();
        generateDataset(firstRadisuList);
        generateDataset(secondRadisuList);
    }

    public void setListener(Callbacks listener) {
        this.listener = listener;
    }

    public void start() {
        handler.postDelayed(this, ANIMATION_STEP);
    }

    @Override
    public void run() {
        if (listener == null) return;
        // change radius
        synchronized (Animation.this) {
            while (firstRadisuList.peek() != null) {
                float newRadius = firstRadisuList.poll();
                listener.ready(newRadius, 0f);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(App.ANIM, "Failed to sleep", e);
                }
//                handler.postDelayed(this, ANIMATION_STEP);
            }
//            while (secondRadisuList.peek() != null) {
//                float newRadius = secondRadisuList.poll();
//                listener.ready(0f, newRadius);
//                handler.postDelayed(this, ANIMATION_STEP);
//            }

            generateDataset(firstRadisuList);
//            generateDataset(secondRadisuList);
        }
    }

    public synchronized Pair<Float, Float> getRadius() {
        if (firstRadisuList.peek() != null) {
            float newRadius = firstRadisuList.poll();
            return new Pair<>(newRadius, 0f);
        } else {
            generateDataset(firstRadisuList);
        }
        return new Pair<>(0f,0f);
    }

    public synchronized Pair<Float, Float> getRadiusSecond() {
        if (secondRadisuList.peek() != null) {
            float newRadius = secondRadisuList.poll();
            return new Pair<>(0f, newRadius);
        } else {
            generateDataset(secondRadisuList);
        }
        return new Pair<>(0f,0f);
    }
//
//    public synchronized Pair<Float, Float> getRadius() {
//        Pair<Float, Float> result = null;
//        if (firstRadisuList.peek() != null) {
//            float newRadius = firstRadisuList.poll();
//            result = new Pair<>(newRadius, 0f);
//        } else if (secondRadisuList.peek() != null){
//            float newRadius = firstRadisuList.poll();
//            result = new Pair<>(0f, newRadius);
//        } else {
//            result = new Pair<>(0f,0f);
//            generateDataset(firstRadisuList);
//        }
//        return result;
//    }

    private void generateDataset(LinkedList<Float> dataset) {
        float newRadius = 0;
        while (newRadius <= config.getRadius()) {
            dataset.add(newRadius++);
        }
    }

    public interface Callbacks {
        void ready(float firstCircle, float secondCircle);
    }


//    private void generateAnimationPath() {
//        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Point point = new Point();
//        windowManager.getDefaultDisplay().getSize(point);
//
//        point.x /= 2;
//        point.y /= 2;
//
//        final float step = 1f;
//        while (point.y > 0) {
//            point.y -= step;
//
//            Point newPoint = new Point(point);
//            newPoint.y = point.y;
//
//            path.add(newPoint);
//        }
//    }

//    private void startAnimation() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (circle) {
//                    if (path.size() == 0) {
//                        Log.d(App.ANIM, "Stop animation");
//                        return; // stop clause
//                    } else {
//                        handler.postDelayed(this, ANIMATION_STEP);
//                    }
//
//                    Point nextPoint = path.pop();
//                    circle.setCenterX(nextPoint.x);
//                    circle.setCenterY(nextPoint.y);
//                    postInvalidate();
//
//
//                }
//            }
//        }, 3 * ANIMATION_STEP);
//    }
}
