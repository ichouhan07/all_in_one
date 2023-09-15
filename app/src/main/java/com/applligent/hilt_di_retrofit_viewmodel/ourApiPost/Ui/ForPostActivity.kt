package com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.applligent.hilt_di_retrofit_viewmodel.LoadingBar.LoadingProgressDialog
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.*
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityForPostBinding
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ItemSelectImageBinding
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.ViewModel.PostApiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException

@AndroidEntryPoint
class ForPostActivity : AppCompatActivity() {
    private val binding: ActivityForPostBinding by lazy { ActivityForPostBinding.inflate(layoutInflater) }

    private var imageString = ""
    private val viewModel: PostApiViewModel by viewModels()
    private lateinit var dialog: LoadingProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        setCustomToolBar()
    }

    private fun setOnClickListener() {
        dialog = LoadingProgressDialog(this)
        loadImageFromUrlFowLoading(this,getProfileImage(),binding.editProfileImg,R.drawable.user_empty_img,binding.imageLoadingProgressBar)
        imageString = getProfileImage()
        binding.editProfileName.setText(getName())
        binding.editProfileAddress.setText(getAddress())
        binding.editProfileBtn.setOnClickListener { selectOptionToGetImage() }
        binding.editSaveBtn.setOnClickListener { saveBtn() }
    }

    private fun selectOptionToGetImage() {
        val dialogBinding: ItemSelectImageBinding =
            ItemSelectImageBinding.inflate(layoutInflater, null, false)
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        dialogBinding.tvDialogSelectImgTitle.text = getString(R.string.add_photo)
        dialogBinding.tvDialogSelectImgDes.text = getString(R.string.take_photo_from_gallery_or_camera)
        dialogBinding.dialogSelectImgClose.setOnClickListener { bottomSheetDialog.dismiss() }
        dialogBinding.dialogSelectImgGalleyBtn.setOnClickListener {
            takeImageFromGallery.launch("image/*")
            bottomSheetDialog.dismiss()
        }
        dialogBinding.dialogSelectImgCameraBtn.setOnClickListener {
            takeImageFromCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            bottomSheetDialog.dismiss()
        }
    }

    //form gallery
    private var takeImageFromGallery = registerForActivityResult<String, Uri>(
        ActivityResultContracts.GetContent()
    ) { result: Uri? ->
        binding.editProfileImg.setImageURI(result)
        if (result != null) {
            var imageBitmap: Bitmap? = null
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, result)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            assert(imageBitmap != null)
            imageString = bitMapToString(imageBitmap)
        }
    }

    //from camera
    private var takeImageFromCamera = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val bundle = result.data!!.extras
            val bitmap = bundle!!["data"] as Bitmap?
            imageString = bitMapToString(bitmap)
            binding.editProfileImg.setImageBitmap(bitmap)
        }
    }

    private fun bitMapToString(bitmap: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun saveBtn() {
        if (isValidSignInDetails()){
            dialog.show()
            val map = HashMap<String, Any>()
            map["profileImage"] = imageString
            map["name"] = binding.editProfileName.text.toString()
            map["address"] = binding.editProfileAddress.text.toString()
            val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImVtYWlsIjoic2hhaHJ1a2hAZ21haWwuY29tIiwidHlwZSI6MX0.rk6rCtLWs7q3Z7cSNC3FxPum_zH5I-SkP0ldI9S66Sk"
            viewModel.editPersonalInfo(map,token)
            viewModel.editLiveData.observe(this) {
                if (it.success){
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    setName(it.data.name)
                    setAddress(it.data.address)
                    setProfileImage(it.data.profileImage)
                    dialog.dismiss()
                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun isValidSignInDetails(): Boolean {
        return if (imageString.isEmpty()){
            Toast.makeText(this, "Select image", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.editProfileName.text.toString().trim().isEmpty()) {
            binding.editProfileName.requestFocus()
            binding.editProfileName.error = "Enter name"
            false
        } /*else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) {
            false
        }*/ else if (binding.editProfileAddress.text.toString().trim().isEmpty()) {
            binding.editProfileAddress.requestFocus()
            binding.editProfileAddress.error = "Enter address"
            false
        } else {
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomToolBar() {
        binding.customToolbar.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.customToolbar.ivNotification.setOnClickListener {
            //Toast.makeText(this, "Clicked Notification", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.ivSetting.setOnClickListener {
            //Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.tvTitle.text = "All_In_One"
    }
}