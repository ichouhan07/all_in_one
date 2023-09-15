package com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Model

data class EditResponse(
    val `data`: DataEditObject,
    val message: String,
    val statusCode: Int,
    val success: Boolean,
    val type: Int
)

data class DataEditObject(
    val aboutMe: String,
    val address: String,
    val city: String,
    val cityName: Any,
    val country: String,
    val countryName: String,
    val created: String,
    val dob: String,
    val email: String,
    val gender: String,
    val hasAccountDetails: Int,
    val hasBiometricEnabled: Int,
    val isOnline: Int,
    val isVerifiedProvider: String,
    val landmark: String,
    val latitude: Int,
    val loginType: Int,
    val longitude: Int,
    val name: String,
    val password: String,
    val paymentMode: Int,
    val phoneNumber: String,
    val profileImage: String,
    val serviceCircle: Int,
    val shiftTiming: String,
    val state: String,
    val stateName: Any,
    val status: Int,
    val type: Int,
    val uid: String,
    val userId: Int
)