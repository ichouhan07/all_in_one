package com.applligent.hilt_di_retrofit_viewmodel.sqlLite

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.toast
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivitySqlAddUserBinding
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ItemSelectImageBinding
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.Database.DatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class SqlAddUserActivity : AppCompatActivity() {
    private val binding : ActivitySqlAddUserBinding by lazy { ActivitySqlAddUserBinding.inflate(layoutInflater) }

    var onDateSetListener: OnDateSetListener? = null
    private var databaseHelper: DatabaseHelper? = null
    private var user: SqlUser? = null

    private var imageString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setCustomToolBar()
        initObjects()

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        binding.sqAddDob.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, R.style.Theme_Holo_Light_Dialog_MinWidth,
                onDateSetListener, year, month, day
            )
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }
        onDateSetListener =
            OnDateSetListener { datePicker, year, month, dayOfMonth ->
                var month = month
                month += 1
                val date = "$dayOfMonth/$month/$year"
                binding.sqAddDob.setText(date)
            }
        binding.sqAddProfileBtn.setOnClickListener { selectOptionToGetImage() }
        binding.sqAddSubmitBtn.setOnClickListener {
            if (isValidAddDetails()) {
                postDataToSQLite()
            }
        }
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(this)
        user = SqlUser()
    }

    private fun selectOptionToGetImage() {
        val dialogBinding: ItemSelectImageBinding =
            ItemSelectImageBinding.inflate(layoutInflater, null, false)
        val bottomSheetDialog = BottomSheetDialog(this, com.applligent.hilt_di_retrofit_viewmodel.R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        dialogBinding.tvDialogSelectImgTitle.text = getString(com.applligent.hilt_di_retrofit_viewmodel.R.string.add_photo)
        dialogBinding.tvDialogSelectImgDes.text = getString(com.applligent.hilt_di_retrofit_viewmodel.R.string.take_photo_from_gallery_or_camera)
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
        binding.sqAddProfileImg.setImageURI(result)
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
            binding.sqAddProfileImg.setImageBitmap(bitmap)
        }
    }

    private fun bitMapToString(bitmap: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun postDataToSQLite() {
        if (!databaseHelper!!.checkUser(binding.sqAddEmail.text.toString().trim())) {
            user?.name = binding.sqAddName.text.toString().trim()
            user?.email = binding.sqAddEmail.text.toString().trim()
            user?.number = binding.sqAddNumber.text.toString().trim()
            user?.dob = binding.sqAddDob.text.toString().trim()
            user?.profileImage = imageString
            val isAdded = databaseHelper!!.addUser(user!!)
            if (isAdded) {
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                val i = Intent(this,SQLiteActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            } else {
                Toast.makeText(this, "User Not Added", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(this, "Email Already Exist in Database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidAddDetails(): Boolean {
        return if (imageString.isEmpty()){
            toast("Select Image")
            false
        }else if (binding.sqAddName.text.toString().trim().isEmpty()){
            binding.sqAddName.requestFocus()
            binding.sqAddName.error = "Enter name"
            false
        }else if (binding.sqAddEmail.text.toString().trim().isEmpty()) {
            binding.sqAddEmail.requestFocus()
            binding.sqAddEmail.error = "Enter email"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.sqAddEmail.text.toString()).matches()) {
            binding.sqAddEmail.requestFocus()
            binding.sqAddEmail.error = "Enter valid email"
            false
        } else if (binding.sqAddNumber.text.toString().trim().isEmpty()) {
            binding.sqAddNumber.requestFocus()
            binding.sqAddNumber.error = "Enter number"
            false
        } else if (binding.sqAddNumber.text.toString().trim().length < 10 ) {
            binding.sqAddNumber.requestFocus()
            binding.sqAddNumber.error = "Enter valid number"
            false
        }
        else if (binding.sqAddDob.text.toString().trim().isEmpty()) {
            binding.sqAddDob.requestFocus()
            binding.sqAddDob.error = "Enter Dob"
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
            Toast.makeText(this, "Clicked Notification", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.ivSetting.setOnClickListener {
            Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.tvTitle.text = "SQL_LITE"
    }
}