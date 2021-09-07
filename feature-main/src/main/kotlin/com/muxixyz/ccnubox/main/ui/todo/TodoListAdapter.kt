package com.muxixyz.ccnubox.main.ui.todo

import android.graphics.Color
import com.muxixyz.ccnubox.main.data.domain.Schedule
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muxixyz.ccnubox.home.databinding.ItemTodoBinding
import java.util.*
import android.util.Log
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout



class TodoListAdapter : ListAdapter<Schedule, RecyclerView.ViewHolder>(ScheduleDiffCallback()) {
    private var edit = false

    private var itemIdSet :MutableSet<String> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val schedule = getItem(position)
        (holder as VH).bind(schedule, edit)
        holder.editCb.isChecked = false
        holder.itemConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"))
        holder.editCb.setOnClickListener {
            Log.d("OnClickListener","click"+position+holder.editCb.isChecked+schedule.id)
            holder.editCb.let {
                if (it.isChecked) {
                    holder.itemConstraintLayout.setBackgroundColor(Color.parseColor("#EBE1FB"))
                    itemIdSet.add(schedule.id)

                } else {
                    holder.itemConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"))
                    itemIdSet.remove(schedule.id)
                }
            }
        }



    }

    fun getCheckIdList():MutableList<String>{
        return itemIdSet.toMutableList()
    }

    fun setEdit(edit: Boolean) {
        this.edit = edit
        notifyDataSetChanged()
    }

    class VH(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

        var editCb: CheckBox = binding.itemTodoEdit

        var itemConstraintLayout: ConstraintLayout = binding.itemConstraintLayout


        //var listener: TodoItemListener? = null

        var schedule: Schedule? = null


        //var color: Int = R.color.colorNoPriority

        private val _time = MutableLiveData<String>()
        val time: LiveData<String> get() = _time



//        fun bind(schedule: Schedule, editing: Boolean) {
//            this.schedule = schedule
//            this.editing = editing
//
//            _time.value = formatTimeMillis(schedule.endTime)
//            color = when (schedule.priority) {
//                1 -> R.color.colorLowPriority
//                2 -> R.color.colorMidPriority
//                3 -> R.color.colorHighPriority
//                else -> R.color.colorNoPriority
//            }

        fun bind(item: Schedule, edit: Boolean) {
            Log.d("bindtime", formatTimeMillis(item.startTime))
            Log.d("bindtime", item.endTime.toString())
            Log.d("bindtime", item.startTime.toString())

            binding.apply {
                schedule = item
                time = formatTimeMillis(item.startTime)
                editing = edit
                executePendingBindings()
            }
        }


        fun formatTimeMillis(time: Calendar?): String {
            if (time == null) return ""

            val curCalendar = Calendar.getInstance()
            with(time) {
                return if (get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR) &&
                    get(Calendar.MONTH) == curCalendar.get(Calendar.MONTH) &&
                    get(Calendar.DAY_OF_MONTH) == curCalendar.get(Calendar.DAY_OF_MONTH)
                ) {
                    "${get(Calendar.HOUR)}:${get(Calendar.MINUTE)}"
                } else {
                    "${get(Calendar.MONTH)}月${get(Calendar.DAY_OF_MONTH)}日"
                }
            }
        }
    }


    private class ScheduleDiffCallback : DiffUtil.ItemCallback<Schedule>() {

        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }
    }

}