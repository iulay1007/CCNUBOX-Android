package com.muxixyz.ccnubox.schedule.data.network

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import com.muxixyz.ccnubox.schedule.data.ScheduleDataSource
import com.muxixyz.ccnubox.schedule.data.domain.Schedule

class ScheduleRemoteRepo(retrofitClients: RetrofitClients) : ScheduleDataSource {

    val scheduleApi = retrofitClients.generalClient.create(IScheduleApi::class.java)

    interface IScheduleApi {

    }

    override suspend fun getAllSchedules(): Result<List<Schedule>> {
        return Result.Success(arrayListOf())
    }

    override suspend fun getScheduleById(id: Int): Result<Schedule> {
        return Result.Success(Schedule(0, "", "", "", "", 0, "", 0, 0, "", "", 0))
    }

    override suspend fun deleteSchedule(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSchedule(id: Int, schedule: Schedule) {
        TODO("Not yet implemented")
    }

    override suspend fun addSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    override suspend fun addSchedules(schedules: List<Schedule>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSchedules() {
        TODO("Not yet implemented")
    }
}