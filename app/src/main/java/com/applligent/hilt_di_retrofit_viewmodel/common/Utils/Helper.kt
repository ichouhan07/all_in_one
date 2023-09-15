package com.applligent.hilt_di_retrofit_viewmodel.common.Utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.applligent.hilt_di_retrofit_viewmodel.BuildConfig
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Ui.ForPostActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import kotlin.math.log

fun Any.log(text: String) {
    if (BuildConfig.DEBUG) Log.d("${this::class.java.simpleName} school_monitoring_debug", text)
}
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
fun Fragment.toast(text: String) {
    requireActivity().toast(text)
}
fun loadImageFromUrl(imageView: ImageView,url: String?,defaultImage: Int,){

}
fun loadImageFromUrlFowLoading(
    context: Context,
    url: String?,
    imageView: ImageView,
    defaultImage: Int,
    progressBar: ProgressBar
){
    //Glide.with(imageView.context).applyDefaultRequestOptions( RequestOptions().placeholder(defaultImage).error(defaultImage)).load(url).into(imageView)
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable?>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }
        })
        .error(defaultImage)
        .into(imageView)
}