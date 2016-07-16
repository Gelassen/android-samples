package com.home.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import java.util.LinkedList;

/**
 * Created by dmitry.kazakov on 7/16/2016.
 */
public class CustomView extends View /*implements Animation.Callbacks*/ {

    private Animation animation;

    private Paint paintDefault;
    private Paint secondPaint;

    private Circle firstCircle;
    private Circle secondCircle;


    public CustomView(Context context) {
        super(context);
        initMemberFields();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMemberFields();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMemberFields();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(App.ANIM, "onDraw \n");

        canvas.drawCircle(firstCircle.getCenterX(), firstCircle.getCenterY(), firstCircle.getRadius(), paintDefault);
        canvas.drawCircle(secondCircle.getCenterX(), secondCircle.getCenterY(), secondCircle.getRadius(), secondPaint);


    }

    private LinkedList<Point> path = new LinkedList<>();
    private LinkedList<Point> path2 = new LinkedList<>();
    private Handler handler;

    private void generateAnimationPath() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);

        point.x /= 2;
        point.y /= 2;

        final float step = 1f;
        while (point.y > 0) {
            point.y -= step;

            Point newPoint = new Point(point);
            newPoint.y = point.y;

            path.add(newPoint);
        }
    }

    private void generateAnimationPath2() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);

        point.x /= 2;
        point.y /= 2;

        final float step = 1f;
        while (point.y > 0) {
            point.y -= step;

            Point newPoint = new Point(point);
            newPoint.y = point.y;

            path2.add(newPoint);
        }
    }

    private int ANIMATION_STEP = 1000 / 60;

    public void startAnimation() {
        generateAnimationPath();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (firstCircle) {
                    if (path.size() == 0) {
                        Log.d(App.ANIM, "Stop animation");
                        return; // stop clause
                    } else {
                        handler.postDelayed(this, ANIMATION_STEP);
                    }

                    Point nextPoint = path.pop();
                    Log.d(App.ANIM, "Animation with " + nextPoint.x + " " + nextPoint.y);

                    Pair<Float, Float> radiusPair = animation.getRadius();
                    firstCircle.setRadius(radiusPair.first);
//                    firstCircle.setCenterY(radiusPair.second);

                    Point nextPoint2 = path2.pop();
                    Log.d(App.ANIM, "Animation with " + nextPoint2.x + " " + nextPoint2.y);

                    Pair<Float, Float> radiusPair2 = animation.getRadiusSecond();
                    secondCircle.setRadius(radiusPair2.second);

                    postInvalidate();


                }
            }
        }, 3 * ANIMATION_STEP);

//        generateAnimationPath2();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (secondCircle) {
//                    if (path2.size() == 0) {
//                        Log.d(App.ANIM, "Stop animation");
//                        return; // stop clause
//                    } else {
//                        handler.postDelayed(this, ANIMATION_STEP);
//                    }
//
//                    Point nextPoint = path2.pop();
//                    Log.d(App.ANIM, "Animation with " + nextPoint.x + " " + nextPoint.y);
//
//                    Pair<Float, Float> radiusPair = animation.getRadiusSecond();
//                    secondCircle.setRadius(radiusPair.second);
////                    firstCircle.setCenterY(radiusPair.second);
//                    postInvalidate();
//
//
//                }
//            }
//        }, 3000);
    }

//    public void startAnimation() {
//        animation.start();
//    }


    private void initMemberFields() {
        paintDefault = new Paint();
        paintDefault.setColor(getContext().getResources().getColor(android.R.color.holo_orange_light));

        secondPaint = new Paint();
        secondPaint.setColor(getContext().getResources().getColor(android.R.color.holo_blue_bright));

        firstCircle = new Circle(getContext());
        secondCircle = new Circle(getContext());

        AnimConfig config = new AnimConfig();
        config.setRadius(firstCircle.getRadius());
        animation = new Animation(config);

        handler = new Handler();
//        animation.setListener(this);
    }
//
////    @Override
//    public void ready(float firstNewRadius, float secondNewRadius) {
//        Log.d(App.ANIM, "firstNewRadius: " + firstNewRadius + " secondNewRadius: " + secondNewRadius);
//        this.firstCircle.setRadius(firstNewRadius);
//        this.secondCircle.setRadius(secondNewRadius);
//        invalidate();
//    }

}
