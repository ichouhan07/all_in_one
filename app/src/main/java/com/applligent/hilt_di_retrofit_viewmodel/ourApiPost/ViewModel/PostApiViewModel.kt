package com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applligent.hilt_di_retrofit_viewmodel.common.Repository.Repository
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Model.EditResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostApiViewModel @Inject constructor(private val repository: Repository)  : ViewModel() {
    val editLiveData : MutableLiveData<EditResponse> = MutableLiveData()

    fun editPersonalInfo(map: HashMap<String, Any>, token: String) {
        viewModelScope.launch {
            repository.editPersonalInfo(map,token)
                .catch {e->
                    Log.d("main", "getPost: ${e.message}")
                }.collect {response->
                    Log.i( "editPersonalInfo23",response.toString())
                    editLiveData.value = response
                }
        }
    }
}