package com.applligent.hilt_di_retrofit_viewmodel.room.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomUserDao {
    @Insert
    fun insert(users: RoomUsers?)

    @Update
    fun update(users: RoomUsers?)

    @Query("delete from RoomUsers where id=:id")
    fun delete(id: Int)

    @get:Query("Select * from RoomUsers")
    val allUsers: List<RoomUsers>?
}