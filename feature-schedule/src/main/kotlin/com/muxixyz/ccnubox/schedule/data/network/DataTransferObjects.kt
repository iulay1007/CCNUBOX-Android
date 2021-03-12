package com.muxixyz.ccnubox.schedule.data.network

import com.muxixyz.ccnubox.schedule.data.database.DatabaseSchedule
import com.muxixyz.ccnubox.schedule.data.domain.Schedule

data class NetworkSchedule(
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
    var updatedAt: String
)

fun List<NetworkSchedule>.asScheduleDomainModel(): List<Schedule> =
    map {
        Schedule(
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
            cellColorId = (0..9).random()
        )
    }

fun List<NetworkSchedule>.asScheduleDatabaseModel(): List<DatabaseSchedule> =
    map {
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
            cellColorId = (0..9).random()
        )
    }

