package com.muxixyz.ccnubox.home.data.network

import com.muxixyz.ccnubox.home.data.database.DatabaseTodo
import com.muxixyz.ccnubox.home.data.domain.Todo
import java.text.DateFormat
import java.util.*

data class NetworkTodoContainer(val todos: List<NetworkTodo>) {
    fun asDatabaseModel(): List<DatabaseTodo> {
        val dateFormat =
            (DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA))
        return todos.map {
            DatabaseTodo(
                id = it.id,
                title = it.title,
                isInternal = it.isInternal,
                startTime = dateFormat.format(it.startTime),
                endTime = dateFormat.format(it.endTime),
                priority = it.priority,
                done = it.done,
                categoryId = it.categoryId,
                cellColorId = it.cellColorId,
                createdAt = if (it.createdAt == null) null else dateFormat.format(it.createdAt),
                updatedAt = if (it.updatedAt == null) null else dateFormat.format(it.updatedAt),
                sortKey = it.sortKey
            )
        }
    }
}

data class NetworkTodo(
    val id: String,
    val title: String,
    val isInternal: Boolean, // false 为时间点，true 为时间段。时间点类型的只有一个 startTime
    val startTime: Date,
    val endTime: Date,
    val priority: Int, // 优先级 0 1 2 3 优先级依次递增
    val done: Boolean,
    val categoryId: Int, // 分类 id
    val cellColorId: Int, // 颜色 id，随机生成一个 0-10 之间的数即可。用于去色板里获取格子颜色
    val createdAt: Date?,
    val updatedAt: Date?,
    val sortKey: String // 用于排序的 key，因为其他产品待办列表里都可以自己调整顺序，所以需要给一个字段。初始值可以给毫秒级时间戳
)

/**
 * Convert Network results to database objects
 */
fun NetworkTodoContainer.asDomainModel(): List<Todo> {
    return todos.map {
        Todo(
            id = it.id,
            title = it.title,
            isInternal = it.isInternal,
            startTime = it.startTime,
            endTime = it.endTime,
            priority = it.priority,
            done = it.done,
            categoryId = it.categoryId,
            cellColorId = it.cellColorId,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            sortKey = it.sortKey
        )
    }
}
