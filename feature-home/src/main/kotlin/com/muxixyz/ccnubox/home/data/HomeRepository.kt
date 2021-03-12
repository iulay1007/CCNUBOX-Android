package com.muxixyz.ccnubox.home.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.home.data.database.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.domain.Todo
import com.muxixyz.ccnubox.home.data.network.HomeRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


class HomeRepository(private val todoLocal: HomeLocalRepo, private val todoRemote: HomeRemoteRepo) {

    private var cachedTodos: ConcurrentMap<String, Todo>? = null

    suspend fun getTodos(forceUpdate: Boolean): Result<List<Todo>> {
        return withContext(Dispatchers.IO) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedTodos?.let { cachedTodos ->
                    return@withContext Result.Success(cachedTodos.values.sortedBy { it.id })
                }
            }

            val newTodos = fetchTodosFromRemoteOrLocal(forceUpdate)

            // Refresh the cache with the new todos
            (newTodos as? Result.Success)?.let { refreshCache(it.data) }

            cachedTodos?.values?.let { todos ->
                return@withContext Result.Success(todos.sortedBy { it.id })
            }

            (newTodos as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    suspend fun fetchTodosFromRemoteOrLocal(forceUpdate: Boolean): Result<List<Todo>> {
        // Remote first
        val remoteTodos = todoRemote.getAllTodos()
        when (remoteTodos) {
            is Result.Error -> ("Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remoteTodos.data)
                return remoteTodos
            }
            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localTodos = todoLocal.getAllTodos()
        if (localTodos is Result.Success) return localTodos
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    suspend fun refreshLocalDataSource(todos: List<Todo>) {
        todoLocal.deleteAllTodos()
        todoLocal.addTodos(todos)
    }

    fun refreshCache(todos: List<Todo>) {
        cachedTodos?.clear()
        todos.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheTodo(todo: Todo): Todo {
        val cachedTodo = Todo(todo.id,todo.title,todo.isInternal,
            todo.startTime, todo.endTime, todo.priority, todo.done, todo.categoryId,
            todo.cellColorId, todo.createdAt, todo.updatedAt, todo.sortKey)
        // Create if it doesn't exist.
        if (cachedTodos == null) {
            cachedTodos = ConcurrentHashMap()
        }
        cachedTodos?.put(cachedTodo.id, cachedTodo)
        return cachedTodo
    }

    private inline fun cacheAndPerform(todo: Todo, perform: (Todo) -> Unit) {
        val cachedTodo = cacheTodo(todo)
        perform(cachedTodo)
    }

    suspend fun addTodo(todo: Todo) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(todo) {
            coroutineScope {
                launch { todoRemote.addTodo(it) }
                launch { todoLocal.addTodo(it) }
            }
        }
    }

    suspend fun completeTodo(todo: Todo) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(todo) {
            it.done = true
            coroutineScope {
                launch { todoRemote.updateTodoDone(it.id, true) }
                launch { todoLocal.updateTodoDone(it.id, true) }
            }
        }
    }

    suspend fun completeTodo(todoId: String) {
        withContext(Dispatchers.IO) {
            getTodoById(todoId)?.let {
                completeTodo(it)
            }
        }
    }

    suspend fun deleteAllTodos() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { todoRemote.deleteAllTodos() }
                launch { todoLocal.deleteAllTodos() }
            }
        }
        cachedTodos?.clear()
    }

    suspend fun deleteTodo(todoId: String) {
        coroutineScope {
            launch { todoRemote.deleteTodo(todoId) }
            launch { todoLocal.deleteTodo(todoId) }
        }

        cachedTodos?.remove(todoId)
    }

    private fun getTodoById(id: String) = cachedTodos?.get(id)
}