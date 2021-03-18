package com.muxixyz.ccnubox.main.data.repository

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.main.data.database.TimetableLocalRepo
import com.muxixyz.ccnubox.main.data.domain.Course
import com.muxixyz.ccnubox.main.data.domain.TimetableRecord
import com.muxixyz.ccnubox.main.data.network.TimetableRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class TimetableRepository(
    private val timetableLocalRepo: TimetableLocalRepo,
    private val timetableRemoteRepo: TimetableRemoteRepo
) {
    private var cachedCourses: ConcurrentMap<String, Course>? = null

    suspend fun getCourses(forceUpdate: Boolean): Result<List<Course>> {
        return withContext(Dispatchers.IO) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedCourses?.let { cachedCourses ->
                    return@withContext Result.Success(cachedCourses.values.sortedBy { it.id })
                }
            }

            val newCourses = fetchCoursesFromRemoteOrLocal(forceUpdate)

            // Refresh the cache with the new courses
            (newCourses as? Result.Success)?.let { refreshCourseCache(it.data) }

            cachedCourses?.values?.let { courses ->
                return@withContext Result.Success(courses.sortedBy { it.id })
            }

            (newCourses as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    suspend fun fetchCoursesFromRemoteOrLocal(forceUpdate: Boolean): Result<List<Course>> {
        // Remote first
        val remoteCourses = timetableRemoteRepo.getAllCourses()
        when (remoteCourses) {
            is Result.Error -> ("Remote data source fetch failed")
            is Result.Success -> {
                refreshCourseLocalDataSource(remoteCourses.data)
                return remoteCourses
            }
            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localCourses = timetableLocalRepo.getAllCourses()
        if (localCourses is Result.Success) return localCourses
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    suspend fun refreshCourseLocalDataSource(courses: List<Course>) {
        timetableLocalRepo.deleteAllCourses()
        timetableLocalRepo.addCourses(courses)
    }

    fun refreshCourseCache(courses: List<Course>) {
        cachedCourses?.clear()
        courses.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheCourse(course: Course): Course {
        val cachedCourse = Course(
            course.id, course.name, course.place, course.teacher, course.dayOfWeek,
            course.courseTime, course.weeks, course.courseTableId
        )
        // Create if it doesn't exist.
        if (cachedCourses == null) {
            cachedCourses = ConcurrentHashMap()
        }
        cachedCourses?.put(cachedCourse.id, cachedCourse)
        return cachedCourse
    }

    private inline fun cacheAndPerform(course: Course, perform: (Course) -> Unit) {
        val cachedCourse = cacheCourse(course)
        perform(cachedCourse)
    }

    suspend fun addCourse(course: Course) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(course) {
            coroutineScope {
                launch { timetableRemoteRepo.addCourse(it) }
                launch { timetableLocalRepo.addCourse(it) }
            }
        }
    }

    suspend fun deleteAllCourses() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { timetableRemoteRepo.deleteAllCourses() }
                launch { timetableLocalRepo.deleteAllCourses() }
            }
        }
        cachedCourses?.clear()
    }

    suspend fun deleteCourse(courseId: String) {
        coroutineScope {
            launch { timetableRemoteRepo.deleteCourse(courseId) }
            launch { timetableLocalRepo.deleteCourse(courseId) }
        }

        cachedCourses?.remove(courseId)
    }

    private fun getCourseById(id: String) = cachedCourses?.get(id)


    private var cachedTimetableRecords: ConcurrentMap<String, TimetableRecord>? = null

    suspend fun getTimetableRecords(forceUpdate: Boolean): Result<List<TimetableRecord>> {
        return withContext(Dispatchers.IO) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedTimetableRecords?.let { cachedTimetableRecords ->
                    return@withContext Result.Success(cachedTimetableRecords.values.sortedBy { it.id })
                }
            }

            val newTimetableRecords = fetchTimetableRecordsFromRemoteOrLocal(forceUpdate)

            // Refresh the cache with the new timetableRecords
            (newTimetableRecords as? Result.Success)?.let { refreshTimetableRecordCache(it.data) }

            cachedTimetableRecords?.values?.let { timetableRecords ->
                return@withContext Result.Success(timetableRecords.sortedBy { it.id })
            }

            (newTimetableRecords as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    suspend fun fetchTimetableRecordsFromRemoteOrLocal(forceUpdate: Boolean): Result<List<TimetableRecord>> {
        // Remote first
        val remoteTimetableRecords = timetableRemoteRepo.getAllTimetableRecords()
        when (remoteTimetableRecords) {
            is Result.Error -> ("Remote data source fetch failed")
            is Result.Success -> {
                refreshTimetableRecordLocalDataSource(remoteTimetableRecords.data)
                return remoteTimetableRecords
            }
            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localTimetableRecords = timetableLocalRepo.getAllTimetableRecords()
        if (localTimetableRecords is Result.Success) return localTimetableRecords
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    suspend fun refreshTimetableRecordLocalDataSource(timetableRecords: List<TimetableRecord>) {
        timetableLocalRepo.deleteAllTimetableRecords()
        timetableLocalRepo.addTimetableRecords(timetableRecords)
    }

    fun refreshTimetableRecordCache(timetableRecords: List<TimetableRecord>) {
        cachedTimetableRecords?.clear()
        timetableRecords.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheTimetableRecord(timetableRecord: TimetableRecord): TimetableRecord {
        val cachedTimetableRecord = TimetableRecord(
            timetableRecord.id,
            timetableRecord.name,
            timetableRecord.sequence,
            timetableRecord.startHour,
            timetableRecord.startMinute,
            timetableRecord.endHour,
            timetableRecord.endMinute,
            timetableRecord.timetableId
        )
        // Create if it doesn't exist.
        if (cachedTimetableRecords == null) {
            cachedTimetableRecords = ConcurrentHashMap()
        }
        cachedTimetableRecords?.put(cachedTimetableRecord.id, cachedTimetableRecord)
        return cachedTimetableRecord
    }

    private inline fun cacheAndPerform(
        timetableRecord: TimetableRecord,
        perform: (TimetableRecord) -> Unit
    ) {
        val cachedTimetableRecord = cacheTimetableRecord(timetableRecord)
        perform(cachedTimetableRecord)
    }

    suspend fun addTimetableRecord(timetableRecord: TimetableRecord) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(timetableRecord) {
            coroutineScope {
                launch { timetableRemoteRepo.addTimetableRecord(it) }
                launch { timetableLocalRepo.addTimetableRecord(it) }
            }
        }
    }

    suspend fun deleteAllTimetableRecords() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { timetableRemoteRepo.deleteAllTimetableRecords() }
                launch { timetableLocalRepo.deleteAllTimetableRecords() }
            }
        }
        cachedTimetableRecords?.clear()
    }

    suspend fun deleteTimetableRecord(timetableRecordId: String) {
        coroutineScope {
            launch { timetableRemoteRepo.deleteTimetableRecord(timetableRecordId) }
            launch { timetableLocalRepo.deleteTimetableRecord(timetableRecordId) }
        }

        cachedTimetableRecords?.remove(timetableRecordId)
    }

    private fun getTimetableRecordById(id: String) = cachedTimetableRecords?.get(id)

}