package com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applligent.hilt_di_retrofit_viewmodel.common.Repository.Repository
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.MainResponseGet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GetViewModel @Inject constructor(private val repository: Repository)  : ViewModel() {
    val getLiveData : MutableLiveData<MainResponseGet> = MutableLiveData()

    fun getCompletedApi(map: HashMap<String, Any>, token: String) {
        viewModelScope.launch {
            repository.getCompletedApi(map,token)
                .catch {e->
                    Log.d("main", "getPost: ${e.message}")
                }.collect {response->
                    getLiveData.value = response
                }
        }
    }
}