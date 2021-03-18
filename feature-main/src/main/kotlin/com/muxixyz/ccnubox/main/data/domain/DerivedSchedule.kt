package com.muxixyz.ccnubox.main.data.domain

import com.muxixyz.ccnubox.main.data.database.DatabaseDerivedSchedule

data class DerivedSchedule(
    var id: Int,
    var name: String,
    var teacher: String,
    var place: String,
    var isInterval: Boolean, // false 为时间点，true 为时间段。时间点类型的只有一个 startTime
    var startTime: String,
    var endTime: String,
    var priority: Int,
    var king: Int, //课程--1，日程--0
    var categoryId: Int,
    var cellColorId: Int,
    var scheduleId: String,
    var courseId: String
)

fun List<DerivedSchedule>.asDerivedScheduleDatabaseModel(): List<DatabaseDerivedSchedule> {
    return map {
        it.asDerivedScheduleDatabaseModel()
    }
}

fun DerivedSchedule.asDerivedScheduleDatabaseModel(): DatabaseDerivedSchedule {
    return let {
        DatabaseDerivedSchedule(
            id = it.id,
            name = it.name,
            teacher = it.teacher,
            place = it.place,
            isInterval = it.isInterval,
            startTime = it.startTime,
            endTime = it.endTime,
            priority = it.priority,
            king = it.king,
            categoryId = it.categoryId,
            cellColorId = it.cellColorId,
            scheduleId = it.scheduleId,
            courseId = it.courseId
        )
    }
}