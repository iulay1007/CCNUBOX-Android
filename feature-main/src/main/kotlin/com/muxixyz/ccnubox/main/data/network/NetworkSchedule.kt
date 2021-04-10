package com.muxixyz.ccnubox.main.data.network

import com.muxixyz.ccnubox.main.data.database.DatabaseSchedule
import com.muxixyz.ccnubox.main.data.domain.Schedule
import java.text.DateFormat
import java.util.*

data class NetworkScheduleContainer(val todos: List<NetworkSchedule>) {
    fun asDatabaseModel(): List<DatabaseSchedule> {
        val dateFormat =
            (DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA))
        return todos.map {
            DatabaseSchedule(
                id = it.id,
                title = it.title,
                content = it.content,
                isInterval = false,
                startTime = it.start_time,
                endTime = it.end_time,
                repeatMode = it.repeat_mode,
                cron = it.cron,
                kind = it.kind,
                priority = it.priority,
                done = it.done != 0,
                categoryId = it.category_id,
                cellColorId = (0..10).random(),
                createdAt = it.created_at,
                updatedAt = it.updated_at,
                sortKey = System.currentTimeMillis().toString()
            )
        }
    }
}

data class NetworkSchedule(
    var id: String,
    var title: String,
    var content: String,
    var start_time: Long?,
    var end_time: Long?,
    var repeat_mode: Int,// repeat_mode(重复规则) 0表示不重复，1表示基于日历规则重复，2表示基于当前学期周数重复
    var cron: String,    // cron表达式， repeat_mode不等于0时有效，表示具体怎么重复
    var priority: Int, // 0-3 优先级依次递增
    var kind: Int,  // 0表示日程，1表示待办
    var done: Int, // 针对待办，0表示未完成，1表示已完成
    var user_id: Int,
    var category_id: Int,     // 0表示默认标签
    var created_at: Long,
    var updated_at: Long?,
    var deleted_at: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkScheduleContainer.asDomainModel(): List<Schedule> {
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA)
    return todos.map {
        Schedule(
            id = it.id,
            title = it.title,
            content = it.content,
            isInterval = false,
            startTime = if (it.start_time == null) null else Calendar.getInstance()
                .also { calendar -> calendar.timeInMillis = it.start_time!! },
            endTime = if (it.end_time == null) null else Calendar.getInstance()
                .also { calendar -> calendar.timeInMillis = it.end_time!! },
            repeatMode = it.repeat_mode,
            cron = it.cron,
            kind = it.kind,
            priority = it.priority,
            done = it.done != 0,
            categoryId = it.category_id,
            cellColorId = (0..10).random(),
            createdAt = Calendar.getInstance()
                .also { calendar -> calendar.timeInMillis = it.created_at },
            updatedAt = if (it.updated_at == null) null else Calendar.getInstance()
                .also { calendar -> calendar.timeInMillis = it.updated_at!! },
            sortKey = System.currentTimeMillis().toString()
        )
    }
}
