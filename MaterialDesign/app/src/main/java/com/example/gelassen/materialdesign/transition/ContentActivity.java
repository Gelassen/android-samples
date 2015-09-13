package com.example.gelassen.materialdesign.transition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.gelassen.materialdesign.R;
import com.example.gelassen.materialdesign.transition.details.DetailsActivity;

/**
 * Created by dmitry.kazakov on 9/9/2015.
 */
public class ContentActivity extends Activity {

    public static void start(Context context, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(context, ContentActivity.class);
        context.startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        final View toolbar = findViewById(R.id.category_title);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair = new Pair<>(toolbar, ContentActivity.this.getString(R.string.transition_toolbar));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ContentActivity.this, pair);
                DetailsActivity.start(ContentActivity.this, options);
            }
        });
        setUpToolbar();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        setActionBar(toolbar);
        //noinspection ConstantConditions
        getActionBar().setDisplayShowTitleEnabled(false);
        final ImageView avatarView = (ImageView) toolbar.findViewById(R.id.avatar);
        avatarView.setImageDrawable(getDrawable(R.drawable.avatar_1_raster));
        ((TextView) toolbar.findViewById(R.id.title)).setText("Gleichmut");
    }

}
