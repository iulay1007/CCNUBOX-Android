package com.muxixyz.ccnubox.timetable.data.domain

import com.muxixyz.ccnubox.timetable.data.database.DatabaseCourse
import com.muxixyz.ccnubox.timetable.data.database.DatabaseTimetableRecord

data class Course(
    var id: String,
    var name: String,
    var place: String,
    var teacher: String,
    var dayOfWeek: Int,  // 一周的第几天，0 是周一
    var courseTime: String, // 比如：1-2，表示第一节到第二节
    var weeks: String, // 1,2,3,4,5 哪几周上课
    var courseTableId: String // 课表 id
)

fun Course.asDatabaseModel(): DatabaseCourse =
    let {
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

fun List<Course>.asCourseDatabaseModel(): List<DatabaseCourse> = map { it.asDatabaseModel() }


data class TimetableRecord(
    var id: String,
    var name: String,
    var sequence: Int,  // 节次
    var startHour: Int,
    var startMinute: Int,
    var endHour: Int,
    var endMinute: Int,
    var timetableId: String // 所属的作息时间表 id
)

fun TimetableRecord.asDatabaseModel(): DatabaseTimetableRecord =
    let {
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

fun List<TimetableRecord>.asTimetableRecordDatabaseModel(): List<DatabaseTimetableRecord> =
    map { it.asDatabaseModel() }