package com.applligent.hilt_di_retrofit_viewmodel.room.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [RoomUsers::class], version = 1)
abstract class RoomUserDatabase : RoomDatabase() {

    abstract val dao: RoomUserDao?

    companion object {
        var INSTANCE: RoomUserDatabase? = null
        fun getInstance(context: Context?): RoomUserDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    context!!,
                    RoomUserDatabase::class.java, "RoomUserDatabase"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}