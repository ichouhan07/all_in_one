package com.applligent.hilt_di_retrofit_viewmodel.fromTutorial

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.Model.Post
import com.applligent.hilt_di_retrofit_viewmodel.common.Repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val postRepository: Repository)  : ViewModel() {
    val postLiveData : MutableLiveData<List<Post>> = MutableLiveData()

    fun getPost(){
        viewModelScope.launch {
            postRepository.getPost()
                .catch {e->
                    Log.d("main", "getPost: ${e.message}")
                }.collect {response->
                    postLiveData.value=response
                }

        }
    }
}