package com.muxixyz.ccnubox.schedule.data.domain

import com.muxixyz.ccnubox.schedule.data.database.DatabaseDerivedSchedule
import com.muxixyz.ccnubox.schedule.data.database.DatabaseSchedule
import java.util.*

/**
 * schedule
 */
data class Schedule(
    var id: Int,
    var title: String,
    var content: String,
    var startTime: String,
    var endTime: String,
    var repeatMode: Int,
    var cron: String,
    var priority: Int,
    var categoryId: Int,
    var createdAt: String,
    var updatedAt: String,
    var cellColorId: Int
)

fun List<Schedule>.asScheduleDatabaseModel(): List<DatabaseSchedule> {
    return map {
        it.asScheduleDatabaseModel()
    }
}

fun Schedule.asScheduleDatabaseModel(): DatabaseSchedule {
    return let {
        DatabaseSchedule(
            id = it.id,
            title = it.title,
            content = it.content,
            startTime = it.startTime,
            endTime = it.endTime,
            repeatMode = it.repeatMode,
            cron = it.cron,
            priority = it.priority,
            categoryId = it.categoryId,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            cellColorId = it.cellColorId
        )
    }
}


/**
 * derived schedule
 */
data class DerivedSchedule(
    var id: Int,
    var name: String,
    var teacher: String,
    var place: String,
    var title: String,
    var content: String,
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
            title = it.title,
            content = it.content,
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