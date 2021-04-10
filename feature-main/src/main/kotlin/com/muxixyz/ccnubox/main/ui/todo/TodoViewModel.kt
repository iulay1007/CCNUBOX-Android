package com.muxixyz.ccnubox.main.ui.todo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muxixyz.android.iokit.Result
import com.muxixyz.android.iokit.succeeded
import com.muxixyz.ccnubox.main.data.domain.Schedule
import com.muxixyz.ccnubox.main.data.repository.ScheduleRepository
import kotlinx.coroutines.launch
import java.util.*

class TodoViewModel(private val repository: ScheduleRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Schedule>>().apply { value = emptyList() }
    val items: LiveData<List<Schedule>>
        get() = _items

    val todoFinishedListLD = MutableLiveData<List<Schedule>>()
    val isLoadingLD = MutableLiveData(false)
    val finishedNumLD = MutableLiveData<Int>(todoFinishedListLD.value?.size)

    fun refreshSchedules() {
        isLoadingLD.value = true
        viewModelScope.launch {
            try {
                val res = repository.getSchedules(true)
                // You may ned to do some model transform for UI here
                // ...
                if (res is Result.Success)
                    _items.postValue(res.data)
                else
                    throw(Exception("getSchedules has something wrong"))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLD.postValue(false)
            }
        }
    }

    fun onEdit() {
        Log.e("edit", "11111111111")
    }

    fun onSort() {
        Log.e("edit", "11111111111")
    }

    fun openItem(id: String) {

    }

    fun completeTodo(id: String, checked: Boolean) {
        viewModelScope.launch {
            repository.completeSchedule(id)
        }
        TODO("deal with result")
    }

    fun getAllSchedules(): Result<List<Schedule>>? {
        Log.e("TdoViewModel", "getAllSchedules()")
        var result: Result<List<Schedule>>? = null
        viewModelScope.launch {
            result = repository.getSchedules(false)
            Log.e("TodoViewModel","1")
        }
        Log.e("TodoViewModel","2")
        return result
    }

}