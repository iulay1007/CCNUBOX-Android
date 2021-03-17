package com.muxixyz.ccnubox.main.data.database

import android.content.Context
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.main.data.domain.DerivedSchedule
import com.muxixyz.ccnubox.main.data.domain.Schedule
import com.muxixyz.ccnubox.main.data.domain.asDatabaseModel
import com.muxixyz.ccnubox.main.data.domain.asDerivedScheduleDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleLocalRepo(
    context: Context,
    private val scheduleDao: ScheduleDao,
    private val derivedScheduleDao: DerivedScheduleDao
) :
    Preferences(context, "schedule") {

    suspend fun getAllSchedules(): Result<List<Schedule>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(scheduleDao.getSchedules().asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getScheduleById(id: String): Result<Schedule> =
        withContext(Dispatchers.IO) {
            try {
                val schedule = scheduleDao.getScheduleById(id)
                if (schedule != null) {
                    return@withContext Result.Success(schedule.asDomainModel())
                } else {
                    return@withContext Result.Error(Exception("Schedule not found!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    suspend fun deleteSchedule(id: String) = withContext(Dispatchers.IO) {
        scheduleDao.deleteScheduleById(id)
    }

    suspend fun updateSchedule(id: String, schedule: Schedule) =
        withContext(Dispatchers.IO) {
            scheduleDao.updateSchedule(schedule.asDatabaseModel())
        }

    suspend fun updateScheduleDone(id: String, done: Boolean) =
        withContext(Dispatchers.IO) {
            scheduleDao.updateDone(id, done)
        }

    suspend fun addSchedule(schedule: Schedule) = withContext(Dispatchers.IO) {
        scheduleDao.insertSchedule(schedule.asDatabaseModel())
    }

    suspend fun addSchedules(schedules: List<Schedule>) = withContext(Dispatchers.IO) {
        scheduleDao.insertSchedules(schedules.asDatabaseModel())
    }

    suspend fun deleteAllSchedules() = withContext(Dispatchers.IO) {
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