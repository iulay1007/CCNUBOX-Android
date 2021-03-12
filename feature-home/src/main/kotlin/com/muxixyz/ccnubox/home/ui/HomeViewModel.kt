package com.muxixyz.ccnubox.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.home.data.HomeRepository
import com.muxixyz.ccnubox.home.data.domain.Todo
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository): ViewModel() {

    val imageListLD = MutableLiveData<List<Todo>>()
    val isLoadingLD = MutableLiveData(false)

    fun refreshTodos() {
        isLoadingLD.value = true
        viewModelScope.launch {
            try {
                val res = repository.getTodos(true)
                // You may ned to do some model transform for UI here
                // ...
                if ( res is Result.Success)
                    imageListLD.postValue(res.data)
                else
                    throw(Exception("getTodos has something wrong"))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLD.postValue(false)
            }
        }
    }

}