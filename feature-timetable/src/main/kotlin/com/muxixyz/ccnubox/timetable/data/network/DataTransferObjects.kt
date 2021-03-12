package com.muxixyz.ccnubox.timetable.data.network

import com.muxixyz.ccnubox.timetable.data.database.DatabaseCourse
import com.muxixyz.ccnubox.timetable.data.database.DatabaseTimetableRecord
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.domain.TimetableRecord

/**
 * course
 */
data class NetworkCourse(
    var id: String,
    var name: String,
    var place: String,
    var teacher: String,
    var dayOfWeek: Int,  // 一周的第几天，0 是周一
    var courseTime: String, // 比如：1-2，表示第一节到第二节
    var weeks: String, // 1,2,3,4,5 哪几周上课
    var courseTableId: String // 课表 id
)

fun List<NetworkCourse>.asCourseDatabaseModel(): List<DatabaseCourse> {
    return map {
        DatabaseCourse(
            id = it.id,
            name = it.name,
            place = it.place,
            teacher = it.teacher,
            dayOfWeek = it.dayOfWeek,
            courseTime = it.courseTime,
            weeks = it.weeks,
            courseTableId = it.courseTableId
        )
    }
}

fun List<NetworkCourse>.asCourseDomainModel(): List<Course> {
    return map {
        Course(
            id = it.id,
            name = it.name,
            place = it.place,
            teacher = it.teacher,
            dayOfWeek = it.dayOfWeek,
            courseTime = it.courseTime,
            weeks = it.weeks,
            courseTableId = it.courseTableId
        )
    }
}

/**
 * timetable record
 */
data class NetworkTimetableRecord(
    var id: String,
    var name: String,
    var sequence: Int,  // 节次
    var startHour: Int,
    var startMinute: Int,
    var endHour: Int,
    var endMinute: Int,
    var timetableId: String // 所属的作息时间表 id
)

fun List<NetworkTimetableRecord>.asTimetableRecordDomainModel(): List<TimetableRecord> =
    map {
        TimetableRecord(
            id = it.id,
            name = it.name,
            sequence = it.sequence,
            startHour = it.startHour,
            startMinute = it.startMinute,
            endHour = it.endHour,
            endMinute = it.endMinute,
            timetableId = it.timetableId
        )
    }

fun List<NetworkTimetableRecord>.asTimetableRecordDatabaseModel(): List<DatabaseTimetableRecord> =
    map {
        DatabaseTimetableRecord(
            id = it.id,
            name = it.name,
            sequence = it.sequence,
            startHour = it.startHour,
            startMinute = it.startMinute,
            endHour = it.endHour,
            endMinute = it.endMinute,
            timetableId = it.timetableId
        )
    }
