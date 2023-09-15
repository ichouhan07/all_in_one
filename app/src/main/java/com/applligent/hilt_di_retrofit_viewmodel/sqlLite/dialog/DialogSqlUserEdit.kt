package com.applligent.hilt_di_retrofit_viewmodel.sqlLite.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.view.Window
import android.widget.*
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.loadImageFromUrl
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.log
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.Database.DatabaseHelper
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.SQLiteActivity
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.SqlUser
import de.hdodenhof.circleimageview.CircleImageView

class DialogSqlUserEdit(
    activity: Activity,
    private val id: Int?,
    private val stringName: String,
    private val stringEmail: String,
    private val stringNumber: String,
    private val stringDob: String,
    private val profileImage: String?,
    private val databaseHelper: DatabaseHelper, ) : Dialog(activity) {

    //var getDialogAllItem:(map: HashMap<String, Any>) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setUpObservers()
    }
    private fun initViews() {
        setUpDialogView()
    }
    private fun setUpObservers() {
        val image = findViewById<CircleImageView>(R.id.sq_edit_profile_img)
        val imageBytes = Base64.decode(profileImage, 0)
        val imageFromBase64String = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        image.setImageBitmap(imageFromBase64String)
        val name = findViewById<EditText>(R.id.sq_edit_name)
        name.setText(stringName)
        val email = findViewById<EditText>(R.id.sq_edit_email)
        email.setText(stringEmail)
        val number = findViewById<EditText>(R.id.sq_edit_number)
        number.setText(stringNumber)
        val dob = findViewById<EditText>(R.id.sq_edit_dob)
        dob.setText(stringDob)
        val dismissBtn = findViewById<Button>(R.id.sq_edit_submit_btn)

        dismissBtn.setOnClickListener {
            dismiss()
            databaseHelper.updateUser(SqlUser(id, name.text.toString(), email.text.toString(),
                number.text.toString(), dob.text.toString(),profileImage))
            val i = Intent(context,SQLiteActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    private fun setUpDialogView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_sql_user_edit)
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}