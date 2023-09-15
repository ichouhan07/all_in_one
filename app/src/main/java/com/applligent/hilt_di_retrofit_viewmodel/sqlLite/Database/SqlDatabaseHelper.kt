package com.applligent.hilt_di_retrofit_viewmodel.sqlLite.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.SqlUser

class DatabaseHelper (context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME   + " TEXT," + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_NUMBER + " TEXT," + COLUMN_USER_DOB   + " TEXT,"
            + COLUMN_USER_IMAGE  + " TEXT"  +
            ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)
        // Create tables again
        onCreate(db)
    }

    fun addUser(user: SqlUser): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_NUMBER, user.number)
        values.put(COLUMN_USER_DOB, user.dob)
        values.put(COLUMN_USER_IMAGE, user.profileImage)

        // Inserting Row
        val result = db.insert(TABLE_USER, null, values)
        db.close()
        return result != -1L
    }

    fun deleteAllRecord(){
        val db = this.writableDatabase
        db.delete(TABLE_USER, null, null)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("delete from $TABLE_USER")
    }
    // Adding user record to list
    // return user list
    //Table to query
    //columns to return
    //columns for the WHERE clause
    //The values for the WHERE clause
    //group the rows
    //filter by row groups
    //The sort order
    // Traversing through all rows and adding to list
    // array of columns to fetch
    @get:SuppressLint("Range")
    val allUser: List<SqlUser>
        get() {
            // array of columns to fetch
            val columns = arrayOf(
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NUMBER,
                COLUMN_USER_DOB,
                COLUMN_USER_IMAGE
            )
            // sorting orders
            val sortOrder = "$COLUMN_USER_NAME ASC"
            val userList: MutableList<SqlUser> = ArrayList()
            val db = this.readableDatabase
            val cursor = db.query(
                TABLE_USER,  //Table to query
                columns,  //columns to return
                null,  //columns for the WHERE clause
                null,  //The values for the WHERE clause
                null,  //group the rows
                null,  //filter by row groups
                sortOrder
            ) //The sort order
            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val user = SqlUser()
                    user.id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt()
                    user.name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                    user.email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
                    user.number = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NUMBER))
                    user.dob = cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOB))
                    user.profileImage = cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE))
                    // Adding user record to list
                    userList.add(user)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            // return user list
            return userList
        }

    fun updateUser(user: SqlUser) {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_NUMBER, user.number)
        values.put(COLUMN_USER_DOB, user.dob)
        values.put(COLUMN_USER_IMAGE, user.profileImage)
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?", arrayOf((user.id.toString())))
        db.close()
    }

    fun deleteUser(user: SqlUser) {
        val db = this.writableDatabase
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?", arrayOf(user.id.toString()))
        db.close()
    }


    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            TABLE_USER,  //Table to query
            columns,  //columns to return
            selection,  //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,  //filter by row groups
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    fun checkUser(email: String, number: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_NUMBER = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, number)
        // query user table with conditions
        val cursor = db.query(
            TABLE_USER,  //Table to query
            columns,  //columns to return
            selection,  //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,  //filter by row groups
            null
        ) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    companion object {
        // Database Version
        const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "UserManager.db"

        // User table name
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_EMAIL = "user_email"
        private const val COLUMN_USER_NUMBER = "user_number"
        private const val COLUMN_USER_DOB = "user_dateOfbirth"
        private const val COLUMN_USER_IMAGE = "profileImage"
        private const val COLUMN_USER_ID = "user_id"
    }
}