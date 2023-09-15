package com.applligent.hilt_di_retrofit_viewmodel.swipeGesture

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivitySwipeGestureBinding
import kotlin.math.abs

class SwipeGestureActivity : AppCompatActivity() {
    private val binding : ActivitySwipeGestureBinding by lazy { ActivitySwipeGestureBinding.inflate(layoutInflater) }
    private var swipeListener: SwipeListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        swipeListener = SwipeListener(binding.relativeLayout,binding.textView)
    }

    class SwipeListener internal constructor(view: View, textView: TextView) : OnTouchListener {
        private var gestureDetector: GestureDetector
        init {
            val threshold = 100
            val velocityThreshold = 100
            val listener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }
                @SuppressLint("SetTextI18n")
                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val xDiff = e2.x - e1.x
                    val yDiff = e2.y - e1.y
                    try {
                        if (abs(xDiff) > abs(yDiff)) {
                            if (abs(xDiff) > threshold && abs(velocityX) > velocityThreshold) {
                                if (xDiff > 0) {
                                    textView.text = "Swiped Right"
                                } else {
                                    textView.text = "Swiped Left"
                                }
                                return true
                            }
                        } else {
                            if (abs(yDiff) > threshold && abs(velocityY) > velocityThreshold) {
                                if (yDiff > 0) {
                                    textView.text = "Swiped Down"
                                } else {
                                    textView.text = "Swiped Up"
                                }
                                return true
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return false
                }
            }
            gestureDetector = GestureDetector(listener)
            view.setOnTouchListener(this)
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(motionEvent)
        }
    }
}