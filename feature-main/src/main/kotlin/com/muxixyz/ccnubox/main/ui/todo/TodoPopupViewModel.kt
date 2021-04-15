package com.muxixyz.ccnubox.main.ui.todo

import android.util.Log
import android.widget.PopupWindow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muxixyz.ccnubox.main.data.domain.Schedule
import com.muxixyz.ccnubox.main.data.repository.ScheduleRepository
import kotlinx.coroutines.launch
import java.util.*

class TodoPopupViewModel(val repository: ScheduleRepository) : ViewModel() {
    private val _tagsVisibility = MutableLiveData<Boolean>(false)
    val tagsVisibility: LiveData<Boolean>
        get() = _tagsVisibility

    private val _tag = MutableLiveData<String>()
    val mTag: LiveData<String> get() = _tag

    val title = MutableLiveData<String>("")

    val date = MutableLiveData<Calendar>()

    val time = MutableLiveData<Calendar>()

    val priority = MutableLiveData(0)

    fun onTagClick(tag: String) {
        _tag.postValue(tag)
    }

    fun showTags() {
        _tagsVisibility.value = !_tagsVisibility.value!!
        Log.e("show tags", _tagsVisibility.value?.toString())
    }

    fun complete() {
        Log.e("complete", "complete")
        val titleValue = title.value ?: ""
        Log.e("complete", titleValue)
        val startTimeValue = time.value
        val priorityValue = priority.value ?: 0
        val schedule = Schedule(
            UUID.randomUUID().toString(),
            titleValue,
            null,
            false,
            startTimeValue,
            null,
            0,
            null,
            1,
            priorityValue,
            false,
            null,
            null,
            Calendar.getInstance(),
            null,
            null
        )
        viewModelScope.launch {
            repository.addSchedule(schedule)
        }
    }

    companion object {
        const val TAG_HOMEWORK = "homework"
        const val TAG_WORK = "work"
        const val TAG_HEALTH = "health"
        const val TAG_SPORT = "sport"
        const val TAG_SHOP = "shop"
        const val TAG_TICKET = "ticket"
        const val TAG_CUSTOM = "custom"
    }
}