package com.muxixyz.ccnubox.home.data.network

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.home.data.HomeDataSource
import com.muxixyz.ccnubox.home.data.domain.Todo
import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import retrofit2.http.GET
import java.util.*

class HomeRemoteRepo(retrofitClients: RetrofitClients) : HomeDataSource {

    private val todoApi = retrofitClients.generalClient.create(ITodoApi::class.java)

    interface ITodoApi {
        @GET("home/")
        suspend fun getTodos(): List<NetworkTodo>
    }

    override suspend fun getAllTodos(): Result<List<Todo>> {
        return Result.Success(arrayListOf())
    }

    override suspend fun getTodoById(id: String): Result<Todo> {
        return Result.Success(Todo("","",false, Date(), Date(), 0 , false, 0, 0 ,Date(), Date(), ""))
    }

    override suspend fun deleteTodo(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(id: String, todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoDone(id: String, done: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun addTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun addTodos(todos: List<Todo>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTodos() {
        TODO("Not yet implemented")
    }
}