package com.applligent.hilt_di_retrofit_viewmodel.room

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.log
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityRoomBinding
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUserDao
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUserDatabase
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUsers

class RoomActivity : AppCompatActivity() , RoomAdapterListener{
    private val binding : ActivityRoomBinding by lazy { ActivityRoomBinding.inflate(layoutInflater) }

    private var userDao: RoomUserDao? = null
    private var userAdapter: RoomUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setCustomToolBar()
        val userDatabase = RoomUserDatabase.getInstance(this)
        userDao = userDatabase?.dao
        userAdapter = RoomUserAdapter(this, this)
        binding.roomUserRecyclerview.adapter = userAdapter
        binding.roomUserRecyclerview.layoutManager = LinearLayoutManager(this)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.roomInsert.setOnClickListener {
            if (binding.roomName.text.toString().isEmpty()) {
                binding.roomName.requestFocus()
                binding.roomName.error = "Enter name"
            } else if (binding.roomEmail.text.toString().isEmpty()) {
                binding.roomEmail.requestFocus()
                binding.roomEmail.error = "Enter email"
            } else {
                val users =
                    RoomUsers(0, binding.roomName.text.toString(), binding.roomEmail.text.toString())
                userDao!!.insert(users)
                userAdapter!!.addUser(users)
                binding.roomName.setText("")
                binding.roomEmail.setText("")
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchData() {
        userAdapter!!.clearData()
        val usersList: List<RoomUsers?>? = userDao?.allUsers
        for (i in usersList!!.indices) {
            val users: RoomUsers? = usersList[i]
            userAdapter!!.addUser(users)
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
        binding.customToolbar.tvTitle.text = "Room"
    }

    override fun update(users: RoomUsers?) {
        val i = Intent(this, RoomUpdateActivity::class.java)
        i.putExtra("model", users)
        startActivity(i)
    }

    override fun delete(id: Int?, position: Int) {
        userDao!!.delete(id!!)
        userAdapter!!.removeUser(position)
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }
}