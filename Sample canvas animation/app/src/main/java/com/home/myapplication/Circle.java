package com.home.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by dmitry.kazakov on 7/16/2016.
 */
class Circle {
    private float centerX;
    private float centerY;
    private float radius;

    public Circle(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);

        centerX = point.x / 2;
        centerY = point.y / 2;
        radius = centerX / 2;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
