package com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.applligent.hilt_di_retrofit_viewmodel.LoadingBar.LoadingProgressDialog
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityForGetBinding
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Adapter.GetAdapter
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.DataEditArray
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.MainResponseGet
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.ViewModel.GetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForGetActivity : AppCompatActivity() {
    private val binding: ActivityForGetBinding by lazy { ActivityForGetBinding.inflate(layoutInflater) }
    private lateinit var getAdapter: GetAdapter
    private val viewModel: GetViewModel by viewModels()
    private lateinit var dialog: LoadingProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dialog = LoadingProgressDialog(this)
        dialog.show()
        setCustomToolBar()
        setUi()
        val map = HashMap<String,Any>()
        map["jobStatus"] = 10
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImVtYWlsIjoic2hhaHJ1a2hAZ21haWwuY29tIiwidHlwZSI6MX0.rk6rCtLWs7q3Z7cSNC3FxPum_zH5I-SkP0ldI9S66Sk"
        viewModel.getCompletedApi(map,token)
        viewModel.getLiveData.observe(this) { response ->
            val mainResponseGet: MainResponseGet = response
            getAdapter.setData(mainResponseGet.data as ArrayList<DataEditArray>)
            dialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomToolBar() {
        binding.customToolbar.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.customToolbar.ivNotification.setOnClickListener {
            Toast.makeText(this, "Clicked Notification", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.ivSetting.setOnClickListener {
            Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.tvTitle.text = "All_In_One"
    }

    private fun setUi() {
        getAdapter= GetAdapter(this, ArrayList())
        binding.getRecyclerview.apply {
            setHasFixedSize(true)
            adapter = getAdapter
        }
    }
}