package com.example.gelassen.materialdesign.transition.details;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.example.gelassen.materialdesign.R;

/**
 * Created by dmitry.kazakov on 9/13/2015.
 */
public class DetailsActivity extends Activity {

    public static void start(Context context, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(context, DetailsActivity.class);
        context.startActivity(intent, optionsCompat.toBundle());
    }

    private ImageView icon;
    private ImageView fab;

    private Interpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Transition sharedElementEnterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.quiz_enter);
        getWindow().setSharedElementEnterTransition(sharedElementEnterTransition);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        interpolator = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.fast_out_slow_in);

        icon = (ImageView) findViewById(R.id.icon);
        icon.animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(interpolator)
                .setStartDelay(300)
                .start();

        fab = (FloatingActionButton) findViewById(R.id.fab_quiz);
        fab.animate()
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(interpolator)
                .setStartDelay(400)
                .start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Scale the icon and fab to 0 size before calling onBackPressed if it exists.
        icon.animate()
                .scaleX(.7f)
                .scaleY(.7f)
                .alpha(0f)
                .setInterpolator(interpolator)
                .start();

        fab.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setInterpolator(interpolator)
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        DetailsActivity.super.onBackPressed();
                    }
                })
                .start();
    }
}
