package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.externalStorage

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applligent.hilt_di_retrofit_viewmodel.databinding.FragmentViewExternalInfoBinding
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ViewExternalInfoFragment : Fragment() {
    private val binding : FragmentViewExternalInfoBinding by lazy { FragmentViewExternalInfoBinding.inflate(layoutInflater) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.showButtonPrivate.setOnClickListener { showPrivateData() }
        binding.showButtonPublic.setOnClickListener { showPublicData() }
        binding.goBackButton.setOnClickListener { back() }
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    fun showPublicData() {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, "geeksData.txt")
        val data: String? = getData(file)
        if (data != null) {
            binding.textViewGetSavedData.text = data
        } else {
            binding.textViewGetSavedData.text = "No Data Found"
        }
    }

    @SuppressLint("SetTextI18n")
    fun showPrivateData() {
        val folder: File? = requireActivity().getExternalFilesDir("GeeksForGeeks")
        val file = File(folder, "gfg.txt")
        val data: String? = getData(file)
        if (data != null) {
            binding.textViewGetSavedData.text = data
        } else {
            binding.textViewGetSavedData.text = "No Data Found"
        }
    }

    fun back() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun getData(myFile: File): String? {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(myFile)
            var i = -1
            val buffer = StringBuffer()
            while (fileInputStream.read().also { i = it } != -1) {
                buffer.append(i.toChar())
            }
            return buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}