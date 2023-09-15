package com.applligent.hilt_di_retrofit_viewmodel.common.Repository

import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.Model.Post
import com.applligent.hilt_di_retrofit_viewmodel.common.Network.ApiService
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.MainResponseGet
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Model.EditResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getPost() : Flow<List<Post>> = flow {
        val response= apiService.getPosts()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getCompletedApi(map: HashMap<String, Any>, token: String): Flow<MainResponseGet> = flow {
        val response= apiService.getCompletedApi(map,token)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun editPersonalInfo(map: HashMap<String, Any>, token: String): Flow<EditResponse> = flow {
        val response= apiService.editPersonalInfo(map,token)
        emit(response)
    }.flowOn(Dispatchers.IO)
}