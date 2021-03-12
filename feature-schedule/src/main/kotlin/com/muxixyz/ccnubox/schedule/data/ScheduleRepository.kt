package com.muxixyz.ccnubox.schedule.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.schedule.data.database.ScheduleLocalRepo
import com.muxixyz.ccnubox.schedule.data.domain.DerivedSchedule
import com.muxixyz.ccnubox.schedule.data.domain.Schedule
import com.muxixyz.ccnubox.schedule.data.network.ScheduleRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class ScheduleRepository(
    private val scheduleRemoteRepo: ScheduleRemoteRepo,
    private val scheduleLocalRepo: ScheduleLocalRepo
) {
    private var cachedSchedules: ConcurrentMap<Int, Schedule>? = null

    suspend fun getSchedules(forceUpdate: Boolean): Result<List<Schedule>> {
        return withContext(Dispatchers.IO) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedSchedules?.let { cachedSchedules ->
                    return@withContext Result.Success(cachedSchedules.values.sortedBy { it.id })
                }
            }

            val newSchedules = fetchSchedulesFromRemoteOrLocal(forceUpdate)

            // Refresh the cache with the new schedules
            (newSchedules as? Result.Success)?.let { refreshCache(it.data) }

            cachedSchedules?.values?.let { schedules ->
                return@withContext Result.Success(schedules.sortedBy { it.id })
            }

            (newSchedules as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    suspend fun fetchSchedulesFromRemoteOrLocal(forceUpdate: Boolean): Result<List<Schedule>> {
        // Remote first
        val remoteSchedules = scheduleRemoteRepo.getAllSchedules()
        when (remoteSchedules) {
            is Result.Error -> ("Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remoteSchedules.data)
                return remoteSchedules
            }
            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localSchedules = scheduleLocalRepo.getAllSchedules()
        if (localSchedules is Result.Success) return localSchedules
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    suspend fun refreshLocalDataSource(schedules: List<Schedule>) {
        scheduleLocalRepo.deleteAllSchedules()
        scheduleLocalRepo.addSchedules(schedules)
    }

    fun refreshCache(schedules: List<Schedule>) {
        cachedSchedules?.clear()
        schedules.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheSchedule(schedule: Schedule): Schedule {
        val cachedSchedule = Schedule(
            schedule.id,
            schedule.title,
            schedule.content,
            schedule.startTime,
            schedule.endTime,
            schedule.repeatMode,
            schedule.cron,
            schedule.priority,
            schedule.categoryId,
            schedule.createdAt,
            schedule.updatedAt,
            schedule.cellColorId
        )
        // Create if it doesn't exist.
        if (cachedSchedules == null) {
            cachedSchedules = ConcurrentHashMap()
        }
        cachedSchedules?.put(cachedSchedule.id, cachedSchedule)
        return cachedSchedule
    }

    private inline fun cacheAndPerform(schedule: Schedule, perform: (Schedule) -> Unit) {
        val cachedSchedule = cacheSchedule(schedule)
        perform(cachedSchedule)
    }

    suspend fun addSchedule(schedule: Schedule) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(schedule) {
            coroutineScope {
                launch { scheduleRemoteRepo.addSchedule(it) }
                launch { scheduleLocalRepo.addSchedule(it) }
            }
        }
    }

    suspend fun deleteAllSchedules() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { scheduleRemoteRepo.deleteAllSchedules() }
                launch { scheduleLocalRepo.deleteAllSchedules() }
            }
        }
        cachedSchedules?.clear()
    }

    suspend fun deleteSchedule(scheduleId: Int) {
        coroutineScope {
            launch { scheduleRemoteRepo.deleteSchedule(scheduleId) }
            launch { scheduleLocalRepo.deleteSchedule(scheduleId) }
        }

        cachedSchedules?.remove(scheduleId)
    }

    private fun getScheduleById(id: Int) = cachedSchedules?.get(id)




    suspend fun getDerivedSchedules(): Result<List<DerivedSchedule>> = withContext(Dispatchers.IO) {
        val localDerivedSchedules = scheduleLocalRepo.getAllDerivedSchedules()
        if (localDerivedSchedules is Result.Success)
            return@withContext localDerivedSchedules
        return@withContext Result.Error(Exception("Error fetching from local"))
    }

    suspend fun addDerivedSchedule(derivedSchedule: DerivedSchedule) {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { scheduleLocalRepo.addDerivedSchedule(derivedSchedule) }
            }
        }
    }

    suspend fun deleteAllDerivedSchedules() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { scheduleLocalRepo.deleteAllDerivedSchedules() }
            }
        }
    }

    suspend fun deleteDerivedSchedule(derivedScheduleId: Int) {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { scheduleLocalRepo.deleteDerivedSchedule(derivedScheduleId) }
            }
        }
    }

    suspend fun getDerivedScheduleById(id: Int) {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { scheduleLocalRepo.getDerivedScheduleById(id) }
            }
        }
    }
}