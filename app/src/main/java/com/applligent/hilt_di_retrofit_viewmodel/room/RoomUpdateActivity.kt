package com.applligent.hilt_di_retrofit_viewmodel.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.log
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityRoomUpdateBinding
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUserDao
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUserDatabase
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUsers

class RoomUpdateActivity : AppCompatActivity() {
    private val binding : ActivityRoomUpdateBinding by lazy { ActivityRoomUpdateBinding.inflate(layoutInflater) }
    private var userDao: RoomUserDao? = null
    private var users: RoomUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val userDatabase = RoomUserDatabase.getInstance(this)
        userDao = userDatabase?.dao
        users = intent.getSerializableExtra("model") as RoomUsers?

        binding.roomName.setText(users?.name)
        binding.roomEmail.setText(users?.email)

        binding.roomUpdate.setOnClickListener {
            val userModel = RoomUsers(
                users!!.id,
                binding.roomName.text.toString(),
                binding.roomEmail.text.toString()
            )
            /*log(users?.id.toString())
            log(binding.roomName.text.toString())
            log(binding.roomEmail.text.toString())*/
            userDao!!.update(userModel)
            finish()
        }
    }
}