package com.harman.motionlayoutanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.view.View
import android.widget.SeekBar
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    var motion_container: MotionLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_container = findViewById<MotionLayout>(R.id.motion_container)
        motion_container!!.setTransitionListener(
                object: MotionLayout.TransitionListener {
                    override fun onTransitionChange(motionLayout: MotionLayout?,
                                                    startId: Int,
                                                    endId: Int,
                                                    progress: Float) {
                        var seekbar = findViewById<SeekBar>(R.id.seekbar)
                        seekbar.progress = ceil(progress * 100).toInt()
                    }

                    override fun onTransitionCompleted(motionLayout: MotionLayout?,
                                                       currentId: Int) {
                        if(currentId == R.id.ending_set) {
                            // Return to original constraint set
                            motion_container!!.transitionToStart()
                        }
                    }
                }
        )
    }

    fun start(v: View) {
        motion_container!!.transitionToEnd()
    }

}
