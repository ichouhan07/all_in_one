package com.applligent.hilt_di_retrofit_viewmodel.common.Utils

import android.content.Context

private const val FILE_NAME = "PrefManager"

fun Context.setId(id: Int){
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("id", id).apply()
}

fun Context.getId() : Int {
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getInt("id", -1)
}

fun Context.setName(name: String){
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("name", name).apply()
}

fun Context.getName() : String {
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString("name", "") ?: ""
}

fun Context.setAddress(address: String){
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("address", address).apply()
}

fun Context.getAddress() : String {
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString("address", "") ?: ""
}

fun Context.setProfileImage(profileImage: String){
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("profileImage", profileImage).apply()
}

fun Context.getProfileImage() : String {
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString("profileImage", "") ?: ""
}

fun Context.setLoginStatus(isLogin: Boolean){
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isLogin", isLogin).apply()
}

fun Context.getLoginStatus() : Boolean {
    val sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isLogin", false)
}
