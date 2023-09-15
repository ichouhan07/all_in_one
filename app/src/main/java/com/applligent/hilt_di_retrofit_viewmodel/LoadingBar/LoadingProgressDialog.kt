package com.applligent.hilt_di_retrofit_viewmodel.LoadingBar

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.applligent.hilt_di_retrofit_viewmodel.R

class LoadingProgressDialog(context: Context) : Dialog(context) {
    init {
        val params = window!!.attributes
        params.gravity = Gravity.CENTER_HORIZONTAL
        window!!.attributes = params
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val view: View = LayoutInflater.from(context).inflate(R.layout.loading_progress_layout, null)
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
        val clock = view.findViewById<ImageView>(R.id.iv_loading_bar)
        clock.animation = rotate
        setContentView(view)
    }
}