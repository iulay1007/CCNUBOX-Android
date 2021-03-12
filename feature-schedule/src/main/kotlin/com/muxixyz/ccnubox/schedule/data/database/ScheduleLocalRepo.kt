package com.muxixyz.ccnubox.schedule.data.database

import android.content.Context
import com.muxixyz.android.iokit.Result
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.ccnubox.schedule.data.ScheduleDataSource
import com.muxixyz.ccnubox.schedule.data.domain.DerivedSchedule
import com.muxixyz.ccnubox.schedule.data.domain.Schedule
import com.muxixyz.ccnubox.schedule.data.domain.asDerivedScheduleDatabaseModel
import com.muxixyz.ccnubox.schedule.data.domain.asScheduleDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleLocalRepo(
    context: Context,
    private val scheduleDao: ScheduleDao,
    private val derivedScheduleDao: DerivedScheduleDao
) :
    Preferences(context, "schedule"), ScheduleDataSource {

    override suspend fun getAllSchedules(): Result<List<Schedule>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(scheduleDao.getSchedules().asScheduleDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getScheduleById(id: Int): Result<Schedule> =
        withContext(Dispatchers.IO) {
            try {
                val schedule = scheduleDao.getScheduleById(id)
                if (schedule != null) {
                    return@withContext Result.Success(schedule.asScheduleDomainModel())
                } else {
                    return@withContext Result.Error(Exception("Schedule not found!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun deleteSchedule(id: Int) = withContext(Dispatchers.IO) {
        scheduleDao.deleteScheduleById(id)
    }

    override suspend fun updateSchedule(id: Int, schedule: Schedule) =
        withContext(Dispatchers.IO) {
            scheduleDao.updateSchedule(schedule.asScheduleDatabaseModel())
        }

    override suspend fun addSchedule(schedule: Schedule) = withContext(Dispatchers.IO) {
        scheduleDao.insertSchedule(schedule.asScheduleDatabaseModel())
    }

    override suspend fun addSchedules(schedules: List<Schedule>) = withContext(Dispatchers.IO) {
        scheduleDao.insertSchedules(schedules.asScheduleDatabaseModel())
    }

    override suspend fun deleteAllSchedules() = withContext(Dispatchers.IO) {
        scheduleDao.deleteAllSchedules()
    }

    suspend fun getAllDerivedSchedules(): Result<List<DerivedSchedule>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(
                    derivedScheduleDao.getDerivedSchedules().asDerivedScheduleDomainModel()
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    suspend fun getDerivedScheduleById(id: Int): Result<DerivedSchedule> =
        withContext(Dispatchers.IO) {
            try {
                val derivedSchedule = derivedScheduleDao.getDerivedScheduleById(id)
                if (derivedSchedule != null) {
                    return@withContext Result.Success(derivedSchedule.asDerivedScheduleDomainModel())
                } else {
                    return@withContext Result.Error(Exception("DerivedSchedule not found!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    suspend fun deleteDerivedSchedule(id: Int) = withContext(Dispatchers.IO) {
        derivedScheduleDao.deleteDerivedScheduleById(id)
    }

    suspend fun updateDerivedSchedule(id: Int, derivedSchedule: DerivedSchedule) =
        withContext(Dispatchers.IO) {
            derivedScheduleDao.updateDerivedSchedule(derivedSchedule.asDerivedScheduleDatabaseModel())
        }

    suspend fun addDerivedSchedule(derivedSchedule: DerivedSchedule) = withContext(Dispatchers.IO) {
        derivedScheduleDao.insertDerivedSchedule(derivedSchedule.asDerivedScheduleDatabaseModel())
    }

    suspend fun addDerivedSchedules(derivedSchedules: List<DerivedSchedule>) =
        withContext(Dispatchers.IO) {
            derivedScheduleDao.insertDerivedSchedules(derivedSchedules.asDerivedScheduleDatabaseModel())
        }

    suspend fun deleteAllDerivedSchedules() = withContext(Dispatchers.IO) {
        derivedScheduleDao.deleteAllDerivedSchedules()
    }
}