package com.harman.motionlayoutanimation

import android.app.Activity
import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.widget.TextView

class TestActiviity : Activity() {

    private var motionLayout: MotionLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        motionLayout = findViewById(R.id.motion_container)
        motionLayout!!.setTransitionListener(null)
    }
}
