package com.applligent.hilt_di_retrofit_viewmodel.LoadingBar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityLoadingBarBinding

class LoadingBarActivity : AppCompatActivity() {
    private val binding : ActivityLoadingBarBinding by lazy { ActivityLoadingBarBinding.inflate(layoutInflater) }
    private lateinit var dialog: LoadingProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dialog = LoadingProgressDialog(this)
        dialog.show()
        Handler(Looper.getMainLooper()).postDelayed(5000) {
            dialog.dismiss()
        }
        binding.okBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}