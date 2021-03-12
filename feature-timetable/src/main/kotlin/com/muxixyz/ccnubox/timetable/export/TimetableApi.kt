package com.muxixyz.ccnubox.timetable.export

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.repository.TimetableRepository

class TimetableApi(private val timetableRepository: TimetableRepository): ITimetableExportApi {
    override suspend fun getTimetable(): List<Course> {
        val timetable = timetableRepository.getCourses(true)
        return if (timetable is Result.Success)
            timetable.data
        else arrayListOf()
    }
}