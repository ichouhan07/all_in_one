package com.applligent.hilt_di_retrofit_viewmodel.sqlLite

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ItemSqlUserBinding
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.Database.DatabaseHelper
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.dialog.DialogSqlUserEdit

class UserAdapter(val context: Context, private var list: ArrayList<SqlUser>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private val databaseHelper = DatabaseHelper(context)
    private var dialogEdit : DialogSqlUserEdit? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ItemSqlUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            try {
                val imageBytes = Base64.decode(item.profileImage, 0)
                val imageFromBase64String = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                this.sqlImage.setImageBitmap(imageFromBase64String)
                this.sqlName.text = item.name
                this.sqlEmail.text = item.email
                this.sqlNumber.text = item.number
                this.sqlDob.text = item.dob
                this.sqlDeleteBtn.setOnClickListener {
                    val builder = AlertDialog.Builder(context)
                    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val alertCustomDialog: View = inflater.inflate(R.layout.alert_dialog_layout, null)
                    builder.setView(alertCustomDialog)
                    val titleDes = alertCustomDialog.findViewById<TextView>(R.id.alert_title_with_des)
                    titleDes.text = "Are you sure you want to delete this user"
                    titleDes.setTextColor(ContextCompat.getColor(context, R.color.black))
                    val yes = alertCustomDialog.findViewById<TextView>(R.id.alert_yes)
                    yes.setTextColor(ContextCompat.getColor(context, R.color.red))
                    val no = alertCustomDialog.findViewById<TextView>(R.id.alert_no)
                    no.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    val alert = builder.create()
                    alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    //for radius or width
                    val displayRectangle = Rect()
                    val window = alert.window
                    window!!.decorView.getWindowVisibleDisplayFrame(displayRectangle)
                    alert.window!!.setLayout(
                        (displayRectangle.width() * 0.8f).toInt(), alert.window!!.attributes.height
                    )
                    alert.show()
                    no.setOnClickListener { alert.dismiss() }
                    yes.setOnClickListener {
                        alert.dismiss()
                        databaseHelper.deleteUser(item)
                        list.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                this.sqlEditBtn.setOnClickListener {
                    val stringName = this.sqlName.text.toString()
                    val stringEmail = this.sqlEmail.text.toString()
                    val stringNumber =  this.sqlNumber.text.toString()
                    val stringDob = this.sqlDob.text.toString()
                    val profileImage = item.profileImage
                    dialogEdit = DialogSqlUserEdit(context as Activity,item.id,stringName
                    ,stringEmail,stringNumber,stringDob,profileImage,databaseHelper)
                    dialogEdit!!.show()
                    notifyDataSetChanged()
                }
            }catch (e :Exception){e.printStackTrace()}
        }
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(val binding: ItemSqlUserBinding) : RecyclerView.ViewHolder(binding.root)
}