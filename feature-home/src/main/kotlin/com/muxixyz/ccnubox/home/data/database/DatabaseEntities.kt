package com.muxixyz.ccnubox.home.data.database

import androidx.room.*
import com.muxixyz.ccnubox.home.data.domain.Todo
import java.text.DateFormat.*
import java.util.*

@Entity
data class DatabaseTodo constructor(
    @PrimaryKey val id: String,
    val title: String,
    val isInternal: Boolean, // false 为时间点，true 为时间段。时间点类型的只有一个 startTime
    val startTime: String,
    val endTime: String,
    val priority: Int, // 优先级 0 1 2 3 优先级依次递增
    val done: Boolean,
    val categoryId: Int, // 分类 id
    val cellColorId: Int, // 颜色 id，随机生成一个 0-10 之间的数即可。用于去色板里获取格子颜色
    val createdAt: String?,
    val updatedAt: String?,
    val sortKey: String // 用于排序的 key，因为其他产品待办列表里都可以自己调整顺序，所以需要给一个字段。初始值可以给毫秒级时间戳
)

fun List<DatabaseTodo>.asDomainModel(): List<Todo> {
    return map {
        it.asDomainModel()
    }
}

fun DatabaseTodo.asDomainModel(): Todo {
    val dateFormat = getDateTimeInstance(LONG, LONG, Locale.CHINA)
    return let {
        Todo(
            id = it.id,
            title = it.title,
            isInternal = it.isInternal,
            startTime = dateFormat.parse(it.startTime)!!,
            endTime = dateFormat.parse(it.endTime)!!,
            priority = it.priority,
            done = it.done,
            categoryId = it.categoryId,
            cellColorId = it.cellColorId,
            createdAt = if (it.createdAt.isNullOrBlank()) null else dateFormat.parse(it.createdAt),
            updatedAt = if (it.updatedAt.isNullOrBlank()) null else dateFormat.parse(it.updatedAt),
            sortKey = it.sortKey
        )
    }
}

@Dao
interface TodoDao {

    @Query("select * from databasetodo")
    suspend fun getTodos(): List<DatabaseTodo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: DatabaseTodo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<DatabaseTodo>)

    @Query("SELECT * FROM databasetodo WHERE id = :todoId")
    suspend fun getTodoById(todoId: String): DatabaseTodo?

    @Update
    suspend fun updateTodo(todo: DatabaseTodo)

    @Query("UPDATE databasetodo SET done = :done WHERE id = :todoId")
    suspend fun updateDone(todoId: String, done: Boolean)

    @Query("delete from databasetodo")
    fun deleteAllTodos()

    @Query("delete from databasetodo where id = :todoId")
    suspend fun deleteTodoById(todoId: String)
}

@Database(entities = [DatabaseTodo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}