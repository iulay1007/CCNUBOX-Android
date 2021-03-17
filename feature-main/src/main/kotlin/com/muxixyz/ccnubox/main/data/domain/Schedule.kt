package com.muxixyz.ccnubox.main.data.domain

import com.muxixyz.ccnubox.main.data.database.DatabaseSchedule
import java.text.DateFormat
import java.text.DateFormat.LONG
import java.util.*

data class Schedule(
    var id: String,
    var title: String,
    var content: String,
    var isInterval: Boolean, // false 为时间点，true 为时间段。时间点类型的只有一个 startTime
    var startTime: Date,
    var endTime: Date,
    var repeatMode: Int, // repeat_mode(重复规则) 0表示不重复，1表示基于日历规则重复，2表示基于当前学期周数重复
    var cron: String?, // 重复的 cron 表达式
    var kind: Int, // 0 是日常，1 是待办
    var priority: Int, // 优先级 0 1 2 3 优先级依次递增
    var done: Boolean,
    var categoryId: Int, // 分类 id
    var cellColorId: Int, // 颜色 id，随机生成一个 0-10 之间的数即可。用于去色板里获取格子颜色
    var createdAt: Date?,
    var updatedAt: Date?,
    var sortKey: String // 用于排序的 key，因为其他产品待办列表里都可以自己调整顺序，所以需要给一个字段。初始值可以给毫秒级时间戳
)

fun Schedule.asDatabaseModel(): DatabaseSchedule {
    val dateFormat = (DateFormat.getDateTimeInstance(LONG, LONG, Locale.CHINA))
    return let {
        DatabaseSchedule(
            id = it.id,
            title = it.title,
            content = it.content,
            isInterval = it.isInterval,
            startTime = dateFormat.format(it.startTime),
            endTime = dateFormat.format(it.endTime),
            repeatMode = it.repeatMode,
            cron = it.cron,
            kind = it.kind,
            priority = it.priority,
            done = it.done,
            categoryId = it.categoryId,
            cellColorId = it.cellColorId,
            createdAt = if (it.createdAt == null) null else dateFormat.format(it.createdAt!!),
            updatedAt = if (it.updatedAt == null) null else dateFormat.format(it.updatedAt!!),
            sortKey = it.sortKey
        )
    }
}

fun List<Schedule>.asDatabaseModel(): List<DatabaseSchedule> =
    map {
        it.asDatabaseModel()
    }