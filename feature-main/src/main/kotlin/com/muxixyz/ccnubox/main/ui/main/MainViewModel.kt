package com.muxixyz.ccnubox.main.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.main.data.domain.Schedule
import com.muxixyz.ccnubox.main.data.repository.ScheduleRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ScheduleRepository): ViewModel() {

    val imageListLD = MutableLiveData<List<Schedule>>()
    val isLoadingLD = MutableLiveData(false)

    fun refreshSchedules() {
        isLoadingLD.value = true
        viewModelScope.launch {
            try {
                val res = repository.getSchedules(true)
                // You may ned to do some model transform for UI here
                // ...
                if ( res is Result.Success)
                    imageListLD.postValue(res.data)
                else
                    throw(Exception("getSchedules has something wrong"))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLD.postValue(false)
            }
        }
    }

}