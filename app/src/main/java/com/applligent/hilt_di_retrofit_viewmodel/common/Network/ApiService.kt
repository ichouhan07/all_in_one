package com.applligent.hilt_di_retrofit_viewmodel.common.Network

import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants.AUTHORIZATION
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants.CUSTOMER_BOOKING_ALL
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants.POSTS
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants.USER_EDIT_CUSTOMER
import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.Model.Post
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.MainResponseGet
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Model.EditResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET(POSTS)
    suspend fun getPosts() : List<Post>

    //for get post
    @POST(CUSTOMER_BOOKING_ALL)
    suspend fun getCompletedApi(@Body map: HashMap<String,Any>, @Header(AUTHORIZATION) token: String): MainResponseGet

    //for post
    @POST(USER_EDIT_CUSTOMER)
    @JvmSuppressWildcards
    suspend fun editPersonalInfo(@Body body: Map<String, Any>,@Header(AUTHORIZATION) token: String): EditResponse

    /*@GET("teacher/get/{school_id}")
   fun getTeacherAndScl(@Path(value = "school_id", encoded = true) schoolId: Int, @Header(AUTHORIZATION) token: String): Call<Any>*/
}