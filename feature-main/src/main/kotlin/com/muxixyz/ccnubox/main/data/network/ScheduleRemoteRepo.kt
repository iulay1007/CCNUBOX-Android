package com.muxixyz.ccnubox.main.data.network

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.main.data.domain.Schedule
import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import retrofit2.http.GET
import java.util.*

class ScheduleRemoteRepo(retrofitClients: RetrofitClients) {

    private val scheduleApi = retrofitClients.generalClient.create(IScheduleApi::class.java)

    interface IScheduleApi {
        @GET("home/")
        suspend fun getSchedules(): List<NetworkSchedule>
    }

    suspend fun getAllSchedules(): Result<List<Schedule>> {
        return Result.Success(arrayListOf())
    }

    suspend fun getScheduleById(id: String): Result<Schedule> {
        return Result.Success(
            Schedule(
                "",
                "",
                "",
                false,
                Date(),
                Date(),
                0,
                "",
                0,
                0,
                false,
                0,
                0,
                Date(),
                Date(),
                ""
            )
        )
    }

    suspend fun deleteSchedule(id: String) {
        TODO("Not yet implemented")
    }

    suspend fun updateSchedule(id: String, schedule: Schedule) {
        TODO("Not yet implemented")
    }

    suspend fun updateScheduleDone(id: String, done: Boolean) {
        TODO("Not yet implemented")
    }

    suspend fun addSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    suspend fun addSchedules(schedules: List<Schedule>) {
        TODO("Not yet implemented")
    }

    suspend fun deleteAllSchedules() {
        TODO("Not yet implemented")
    }
}