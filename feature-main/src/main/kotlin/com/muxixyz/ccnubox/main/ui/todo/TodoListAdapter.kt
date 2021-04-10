package com.muxixyz.ccnubox.main.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.muxixyz.ccnubox.home.databinding.ItemTodoBinding
import com.muxixyz.ccnubox.main.data.domain.Schedule

class TodoListAdapter(val mTodos: MutableList<Schedule>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VH(binding)
    }

    override fun getItemCount(): Int {
        return mTodos.size
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder.binding) {
            schedule = mTodos[position]
            listener = object : TodoItemListener {
                override fun onItemClick(todo: Schedule) {
                    viewModel.openItem(todo.id)
                }

                override fun onCheckChanged(view: View, todo: Schedule) {
                    val checked = (view as CheckBox).isChecked
                    viewModel.completeTodo(todo.id, checked)
                }
            }
        }
    }

    fun setTodos(todos: List<Schedule>) {
        mTodos.clear()
        mTodos.addAll(todos)
        notifyDataSetChanged()
    }
}

class VH(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

interface TodoItemListener {
    fun onItemClick(todo: Schedule)
    fun onCheckChanged(view: View, todo: Schedule)
}