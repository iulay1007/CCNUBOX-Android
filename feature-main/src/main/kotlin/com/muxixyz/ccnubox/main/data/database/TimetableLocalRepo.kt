package com.muxixyz.ccnubox.main.data.database

import android.content.Context
import com.muxixyz.android.iokit.Result
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.ccnubox.main.data.database.CourseDao
import com.muxixyz.ccnubox.main.data.database.asCourseDomainModel
import com.muxixyz.ccnubox.main.data.database.asDomainModel
import com.muxixyz.ccnubox.main.data.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TimetableLocalRepo(
    context: Context,
    private val courseDao: CourseDao,
    private val timetableRecordDao: TimetableRecordDao
) : Preferences(context, "timetable") {

    suspend fun getAllCourses(): Result<List<Course>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(courseDao.getCourses().asCourseDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getCourseById(id: String): Result<Course> = withContext(Dispatchers.IO) {
        try {
            val course = courseDao.getCourseById(id)
            if (course != null) {
                return@withContext Result.Success(course.asDomainModel())
            } else {
                return@withContext Result.Error(Exception("Course not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    suspend fun deleteCourse(id: String) = withContext(Dispatchers.IO) {
        courseDao.deleteCourseById(id)
    }

    suspend fun updateCourse(id: String, course: Course) = withContext(Dispatchers.IO) {
        courseDao.updateCourse(course.asDatabaseModel())
    }

    suspend fun addCourse(course: Course) = withContext(Dispatchers.IO) {
        courseDao.insertCourse(course.asDatabaseModel())
    }

    suspend fun addCourses(courses: List<Course>) = withContext(Dispatchers.IO) {
        courseDao.insertCourses(courses.asCourseDatabaseModel())
    }

    suspend fun deleteAllCourses() = withContext(Dispatchers.IO) {
        courseDao.deleteAllCourses()
    }

    suspend fun getAllTimetableRecords(): Result<List<TimetableRecord>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(timetableRecordDao.getTimetableRecords().asTimetableDomainModel())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    suspend fun getTimetableRecordById(id: String): Result<TimetableRecord> =
        withContext(Dispatchers.IO) {
            try {
                val timetableRecord = timetableRecordDao.getTimetableRecordById(id)
                if (timetableRecord != null) {
                    return@withContext Result.Success(timetableRecord.asDomainModel())
                } else {
                    return@withContext Result.Error(Exception("TimetableRecord not found!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    suspend fun deleteTimetableRecord(id: String) = withContext(Dispatchers.IO) {
        timetableRecordDao.deleteTimetableRecordById(id)
    }

    suspend fun updateTimetableRecord(id: String, timetableRecord: TimetableRecord) =
        withContext(Dispatchers.IO) {
            timetableRecordDao.updateTimetableRecord(timetableRecord.asDatabaseModel())
        }

    suspend fun addTimetableRecord(timetableRecord: TimetableRecord) =
        withContext(Dispatchers.IO) {
            timetableRecordDao.insertTimetableRecord(timetableRecord.asDatabaseModel())
        }

    suspend fun addTimetableRecords(timetableRecords: List<TimetableRecord>) =
        withContext(Dispatchers.IO) {
            timetableRecordDao.insertTimetableRecords(timetableRecords.asTimetableRecordDatabaseModel())
        }

    suspend fun deleteAllTimetableRecords() = withContext(Dispatchers.IO) {
        timetableRecordDao.deleteAllTimetableRecords()
    }
}