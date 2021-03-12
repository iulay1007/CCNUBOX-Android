package com.muxixyz.ccnubox.timetable.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.domain.TimetableRecord

interface TimetableDataSource {
    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun getCourseById(id :String) : Result<Course>

    suspend fun deleteCourse(id:String)

    suspend fun updateCourse(id:String, course: Course)

    suspend fun addCourse(course:Course)

    suspend fun addCourses(courses:List<Course>)

    suspend fun deleteAllCourses()

    suspend fun getAllTimetableRecords(): Result<List<TimetableRecord>>

    suspend fun getTimetableRecordById(id :String) : Result<TimetableRecord>

    suspend fun deleteTimetableRecord(id:String)

    suspend fun updateTimetableRecord(id:String, timetableRecord: TimetableRecord)

    suspend fun addTimetableRecord(timetableRecord:TimetableRecord)

    suspend fun addTimetableRecords(timetableRecords:List<TimetableRecord>)

    suspend fun deleteAllTimetableRecords()
}