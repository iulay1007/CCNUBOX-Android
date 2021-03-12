package com.muxixyz.ccnubox.schedule.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.schedule.data.domain.Schedule

interface ScheduleDataSource {
    suspend fun getAllSchedules(): Result<List<Schedule>>

    suspend fun getScheduleById(id: Int): Result<Schedule>

    suspend fun deleteSchedule(id: Int)

    suspend fun updateSchedule(id: Int, schedule: Schedule)

    suspend fun addSchedule(schedule: Schedule)

    suspend fun addSchedules(schedules: List<Schedule>)

    suspend fun deleteAllSchedules()
}