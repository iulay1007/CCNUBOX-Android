package com.muxixyz.ccnubox.home.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.home.data.domain.Todo

interface HomeDataSource {
    suspend fun getAllTodos(): Result<List<Todo>>

    suspend fun getTodoById(id :String) : Result<Todo>

    suspend fun deleteTodo(id:String)

    suspend fun updateTodo(id:String, todo: Todo)

    suspend fun updateTodoDone(id:String, done: Boolean)

    suspend fun addTodo(todo:Todo)

    suspend fun addTodos(todos:List<Todo>)

    suspend fun deleteAllTodos()
}