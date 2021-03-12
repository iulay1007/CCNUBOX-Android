package com.muxixyz.ccnubox.timetable.data.database

import android.content.Context
import com.muxixyz.android.iokit.Result
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.ccnubox.timetable.data.TimetableDataSource
import com.muxixyz.ccnubox.timetable.data.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TimetableLocalRepo(context: Context, private val courseDao: CourseDao, private val timetableRecordDao: TimetableRecordDao) : Preferences(context, "timetable"),
    TimetableDataSource {

    override suspend fun getAllCourses(): Result<List<Course>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(courseDao.getCourses().asCourseDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCourseById(id: String): Result<Course> = withContext(Dispatchers.IO) {
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

    override suspend fun deleteCourse(id: String) = withContext(Dispatchers.IO) {
        courseDao.deleteCourseById(id)
    }

    override suspend fun updateCourse(id: String, course: Course) = withContext(Dispatchers.IO) {
        courseDao.updateCourse(course.asDatabaseModel())
    }

    override suspend fun addCourse(course: Course) = withContext(Dispatchers.IO) {
        courseDao.insertCourse(course.asDatabaseModel())
    }

    override suspend fun addCourses(courses: List<Course>) = withContext(Dispatchers.IO) {
        courseDao.insertCourses(courses.asCourseDatabaseModel())
    }

    override suspend fun deleteAllCourses() = withContext(Dispatchers.IO) {
        courseDao.deleteAllCourses()
    }

    override suspend fun getAllTimetableRecords(): Result<List<TimetableRecord>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(timetableRecordDao.getTimetableRecords().asTimetableDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTimetableRecordById(id: String): Result<TimetableRecord> = withContext(Dispatchers.IO) {
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

    override suspend fun deleteTimetableRecord(id: String) = withContext(Dispatchers.IO) {
        timetableRecordDao.deleteTimetableRecordById(id)
    }

    override suspend fun updateTimetableRecord(id: String, timetableRecord: TimetableRecord) = withContext(Dispatchers.IO) {
        timetableRecordDao.updateTimetableRecord(timetableRecord.asDatabaseModel())
    }

    override suspend fun addTimetableRecord(timetableRecord: TimetableRecord) = withContext(Dispatchers.IO) {
        timetableRecordDao.insertTimetableRecord(timetableRecord.asDatabaseModel())
    }

    override suspend fun addTimetableRecords(timetableRecords: List<TimetableRecord>) = withContext(Dispatchers.IO) {
        timetableRecordDao.insertTimetableRecords(timetableRecords.asTimetableRecordDatabaseModel())
    }

    override suspend fun deleteAllTimetableRecords() = withContext(Dispatchers.IO) {
        timetableRecordDao.deleteAllTimetableRecords()
    }
}