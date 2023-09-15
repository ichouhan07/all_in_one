package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.internalStorage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.applligent.hilt_di_retrofit_viewmodel.databinding.FragmentInternalStorageBinding
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class InternalStorageFragment : Fragment() {
    private val binding : FragmentInternalStorageBinding by lazy { FragmentInternalStorageBinding.inflate(layoutInflater) }
    private val filename = "demoFile.txt"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.internalReadButton.setOnClickListener{
            onClick(it)
        }
        binding.internalWriteButton.setOnClickListener{
            if (binding.internalUserInput.text.toString().isEmpty()) {
                binding.internalUserInput.requestFocus()
                binding.internalUserInput.error = "Enter something"
                return@setOnClickListener
            }
            onClick(it)
        }
        return binding.root
    }

    private fun onClick(view: View) {
        val b = view as Button
        val bText = b.text.toString()
        when (bText.lowercase(Locale.getDefault())) {
            "write" -> {
                writeData()
            }
            "read" -> {
                readData()
            }
        }
    }

    private fun printMessage(m: String?) {
        Toast.makeText(requireContext(), m, Toast.LENGTH_LONG).show()
    }

    private fun writeData() {
        try {
            val fos: FileOutputStream = requireActivity().openFileOutput(filename, Context.MODE_PRIVATE)
            val data: String =  binding.internalUserInput.text.toString()
            fos.write(data.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.internalUserInput.setText("")
        printMessage("writing to file " + filename + "completed...")
    }

    private fun readData() {
        try {
            val fin: FileInputStream = requireActivity().openFileInput(filename)
            var a: Int
            val temp = StringBuilder()
            while (fin.read().also { a = it } != -1) {
                temp.append(a.toChar())
            }
            binding.internalContent.text = temp.toString()
            fin.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        printMessage("reading to file $filename completed..")
    }
}