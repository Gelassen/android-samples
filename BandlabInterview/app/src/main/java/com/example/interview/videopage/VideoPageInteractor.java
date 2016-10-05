package com.example.interview.videopage;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * The intent of this class is encapsulate biz logic and data suppliers from controller
 *
 * Created by John on 10/5/2016.
 */
public class VideoPageInteractor {

    public Point getDisplaySize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

}
