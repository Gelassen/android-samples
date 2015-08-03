package com.example.gelassen.materialdesign;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.gelassen.materialdesign.floattoolbar.adapters.RecyclerAdapter;
import com.example.gelassen.materialdesign.floattoolbar.listeners.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;


public class RipleActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_riple);

    }


}
