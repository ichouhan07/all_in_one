package com.applligent.hilt_di_retrofit_viewmodel.room.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class RoomUsers(
    @field:PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var email: String
) : Serializable