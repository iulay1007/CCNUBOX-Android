package com.muxixyz.ccnubox.home.data.database

import android.content.Context
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.home.data.HomeDataSource
import com.muxixyz.ccnubox.home.data.domain.Todo
import com.muxixyz.ccnubox.home.data.domain.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeLocalRepo(context: Context, private val todoDao: TodoDao) : Preferences(context, "home"),
    HomeDataSource {

    override suspend fun getAllTodos(): Result<List<Todo>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(todoDao.getTodos().asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTodoById(id: String): Result<Todo> = withContext(Dispatchers.IO) {
        try {
            val todo = todoDao.getTodoById(id)
            if (todo != null) {
                return@withContext Result.Success(todo.asDomainModel())
            } else {
                return@withContext Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun deleteTodo(id: String) = withContext(Dispatchers.IO) {
        todoDao.deleteTodoById(id)
    }

    override suspend fun updateTodo(id: String, todo: Todo) = withContext(Dispatchers.IO) {
        todoDao.updateTodo(todo.asDatabaseModel())
    }

    override suspend fun updateTodoDone(id: String, done: Boolean) = withContext(Dispatchers.IO) {
        todoDao.updateDone(id, done)
    }

    override suspend fun addTodo(todo: Todo) = withContext(Dispatchers.IO) {
        todoDao.insertTodo(todo.asDatabaseModel())
    }

    override suspend fun addTodos(todos: List<Todo>) = withContext(Dispatchers.IO) {
        todoDao.insertTodos(todos.asDatabaseModel())
    }

    override suspend fun deleteAllTodos() = withContext(Dispatchers.IO) {
        todoDao.deleteAllTodos()
    }
}