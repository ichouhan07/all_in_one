package com.applligent.hilt_di_retrofit_viewmodel.room

import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUsers

open interface RoomAdapterListener {
    fun update(users: RoomUsers?)
    fun delete(id: Int?, position: Int)
}