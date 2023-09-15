package com.applligent.hilt_di_retrofit_viewmodel.clickAnimationOrWidget.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityClickAnimationBinding

class ClickAnimationActivity : AppCompatActivity() {
    private val binding : ActivityClickAnimationBinding by lazy { ActivityClickAnimationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}