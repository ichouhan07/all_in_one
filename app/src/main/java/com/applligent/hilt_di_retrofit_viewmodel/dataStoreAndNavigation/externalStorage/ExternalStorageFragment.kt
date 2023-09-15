package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.externalStorage

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.databinding.FragmentExternalStorageBinding
import java.io.File
import java.io.FileOutputStream

class ExternalStorageFragment : Fragment() {
    private val binding : FragmentExternalStorageBinding by lazy { FragmentExternalStorageBinding.inflate(layoutInflater) }
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.saveButtonPrivate.setOnClickListener {
            if (binding.editTextData.text.toString().isEmpty()) {
                binding.editTextData.requestFocus()
                binding.editTextData.error = "Enter something"
                return@setOnClickListener
            }
            savePrivately()
        }
        binding.saveButtonPublic.setOnClickListener {
            if (binding.editTextData.text.toString().isEmpty()) {
                binding.editTextData.requestFocus()
                binding.editTextData.error = "Enter something"
                return@setOnClickListener
            }
            savePublicly()
        }
        binding.viewButton.setOnClickListener {
            viewInformation()
        }
        return binding.root
    }

    private fun savePublicly() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_PERMISSION_CODE
        )
        val editTextData: String = binding.editTextData.text.toString()
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, "geeksData.txt")
        writeTextData(file, editTextData)
        binding.editTextData.setText("")
    }
    private fun savePrivately() {
        val editTextData: String = binding.editTextData.text.toString()
        val folder: File? = requireActivity().getExternalFilesDir("GeeksForGeeks")
        val file = File(folder, "gfg.txt")
        writeTextData(file, editTextData)
        binding.editTextData.setText("")
    }

    private fun viewInformation() {
        findNavController().navigate(R.id.action_externalStorageFragment_to_viewExternalInfoFragment,null)
    }

    private fun writeTextData(file: File, data: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data.toByteArray())
            Toast.makeText(requireContext(), "Done" + file.absolutePath, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}