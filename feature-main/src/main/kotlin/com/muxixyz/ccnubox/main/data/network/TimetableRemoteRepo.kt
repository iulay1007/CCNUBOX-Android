package com.muxixyz.ccnubox.main.data.network

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import com.muxixyz.ccnubox.main.data.domain.Course
import com.muxixyz.ccnubox.main.data.domain.TimetableRecord
import retrofit2.http.GET

class TimetableRemoteRepo(retrofitClients: RetrofitClients) {

    private val courseApi = retrofitClients.generalClient.create(ICourseApi::class.java)
    private val timetableRecordApi =
        retrofitClients.generalClient.create(ITimetableRecordApi::class.java)

    interface ICourseApi {
        @GET("course/")
        suspend fun getCourses(): List<NetworkCourse>
    }

    interface ITimetableRecordApi {

    }

     suspend fun getAllCourses(): Result<List<Course>> {
        return Result.Success(arrayListOf())
    }

     suspend fun getCourseById(id: String): Result<Course> {
        return Result.Success(Course("", "", "", "", 0, "", "", ""))
    }

     suspend fun deleteCourse(id: String) {
        TODO("Not yet implemented")
    }

     suspend fun updateCourse(id: String, course: Course) {
        TODO("Not yet implemented")
    }

     suspend fun addCourse(course: Course) {
        TODO("Not yet implemented")
    }

     suspend fun addCourses(courses: List<Course>) {
        TODO("Not yet implemented")
    }

     suspend fun deleteAllCourses() {
        TODO("Not yet implemented")
    }

     suspend fun getAllTimetableRecords(): Result<List<TimetableRecord>> {
        return Result.Success(arrayListOf())
    }

     suspend fun getTimetableRecordById(id: String): Result<TimetableRecord> {
        return Result.Success(TimetableRecord("", "", 0, 0, 0, 0, 0, ""))
    }

     suspend fun deleteTimetableRecord(id: String) {
        TODO("Not yet implemented")
    }

     suspend fun updateTimetableRecord(id: String, timetableRecord: TimetableRecord) {
        TODO("Not yet implemented")
    }

     suspend fun addTimetableRecord(timetableRecord: TimetableRecord) {
        TODO("Not yet implemented")
    }

     suspend fun addTimetableRecords(timetableRecords: List<TimetableRecord>) {
        TODO("Not yet implemented")
    }

     suspend fun deleteAllTimetableRecords() {
        TODO("Not yet implemented")
    }
}