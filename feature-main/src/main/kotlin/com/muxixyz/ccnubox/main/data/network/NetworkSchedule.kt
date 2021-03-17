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
                id = it.id.toString(),
                title = it.title,
                content = it.content,
                isInterval = false,
                startTime = dateFormat.format(it.start_time)!!,
                endTime = dateFormat.format(it.end_time)!!,
                repeatMode = it.repeat_mode,
                cron = it.cron,
                kind = it.kind,
                priority = it.priority,
                done = it.done != 0,
                categoryId = it.category_id,
                cellColorId = (0..10).random(),
                createdAt = if (it.created_at.isNullOrEmpty()) null else dateFormat.format(it.created_at!!),
                updatedAt = if (it.updated_at.isNullOrEmpty()) null else dateFormat.format(it.updated_at!!),
                sortKey = System.currentTimeMillis().toString()
            )
        }
    }
}

data class NetworkSchedule(
    var id: Int,
    var title: String,
    var content: String,
    var start_time: String,
    var end_time: String,
    var repeat_mode: Int,// repeat_mode(重复规则) 0表示不重复，1表示基于日历规则重复，2表示基于当前学期周数重复
    var cron: String,    // cron表达式， repeat_mode不等于0时有效，表示具体怎么重复
    var priority: Int, // 0-3 优先级依次递增
    var kind: Int,  // 0表示日程，1表示待办
    var done: Int, // 针对待办，0表示未完成，1表示已完成
    var user_id: Int,
    var category_id: Int,     // 0表示默认标签
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkScheduleContainer.asDomainModel(): List<Schedule> {
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA)
    return todos.map {
        Schedule(
            id = it.id.toString(),
            title = it.title,
            content = it.content,
            isInterval = false,
            startTime = dateFormat.parse(it.start_time)!!,
            endTime = dateFormat.parse(it.end_time)!!,
            repeatMode = it.repeat_mode,
            cron = it.cron,
            kind = it.kind,
            priority = it.priority,
            done = it.done != 0,
            categoryId = it.category_id,
            cellColorId = (0..10).random(),
            createdAt = if (it.created_at.isNullOrEmpty()) null else dateFormat.parse(it.created_at!!),
            updatedAt = if (it.updated_at.isNullOrEmpty()) null else dateFormat.parse(it.updated_at!!),
            sortKey = System.currentTimeMillis().toString()
        )
    }
}
