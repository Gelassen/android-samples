package com.example.gelassen.materialdesign;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

import com.example.gelassen.materialdesign.transition.ContentActivity;

/**
 * Created by dmitry.kazakov on 9/5/2015.
 */
public class SigninActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void onFabClick(View view) {
        view.animate()
                .scaleX(0)
                .scaleY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        performTrnsition();
                    }
                }).start();

    }

    private void performTrnsition() {
        View avatar = findViewById(R.id.avatar);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, new Pair<View, String>(avatar, avatar.getTransitionName())
        );
        ContentActivity.start(SigninActivity.this, activityOptionsCompat);

    }
}
