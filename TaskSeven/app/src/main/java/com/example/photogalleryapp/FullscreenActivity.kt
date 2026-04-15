package com.example.photogalleryapp

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    private var scaleFactor = 1.0f
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imgView = findViewById<ImageView>(R.id.FullscreenImage)
        val resId = intent.getIntExtra("IMG_RES", 0)

        if (resId != 0) {
            imgView.setImageResource(resId)
        }

        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(1.0f, 5.0f) // Min 1x, Max 5x zoom
                imgView.scaleX = scaleFactor
                imgView.scaleY = scaleFactor
                return true
            }
        })

        // Set touch listener directly on the image to capture pinch gestures
        imgView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }
}