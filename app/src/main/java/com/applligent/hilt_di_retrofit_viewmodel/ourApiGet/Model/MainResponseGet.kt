package com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model

data class MainResponseGet(
    val `data`: List<DataEditArray>,
    val message: String,
    val statusCode: Int,
    val success: Boolean,
    val type: Int
)
data class DataEditArray(
    val Ratings: Double,
    val customerId: Int,
    val date: String,
    val jobAmount: Int,
    val jobDescription: String,
    val jobId: Int,
    val jobStatus: Int,
    val jobTime: List<JobTime>,
    val jobTitle: String,
    val jobType: Int,
    val name: String,
    val phoneNumber: String,
    val profileImage: String,
    val providerId: Int,
    val timeSlot: String,
    val timeSlotId: Int
)
data class JobTime(
    val id: Int,
    val jobId: Int,
    val jobStatus: Int,
    val jobStatusName: String,
    val jobStatusTime: String
)