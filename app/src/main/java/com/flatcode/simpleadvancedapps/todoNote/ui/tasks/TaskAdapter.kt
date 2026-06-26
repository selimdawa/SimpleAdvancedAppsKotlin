package com.flatcode.simpleadvancedapps.todoNote.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.databinding.ItemTaskBinding
import com.flatcode.simpleadvancedapps.todoNote.data.Task

class TaskAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallBack()) {

    class TaskViewHolder(
        private val binding: ItemTaskBinding,
        private val listener: OnItemClickListener,
        private val adapter: TaskAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = adapter.getItem(position)
                    listener.onItemClick(task)
                }
            }

            binding.taskCheckBox.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = adapter.getItem(position)
                    listener.onChecBoxClick(task, binding.taskCheckBox.isChecked)
                }
            }
        }

        fun bind(task: Task) {
            binding.taskCheckBox.isChecked = task.completed
            binding.taskName.text = task.name
            binding.taskName.paint.isStrikeThruText = task.completed
            binding.taskPriority.isVisible = task.important
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onChecBoxClick(task: Task, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, listener, this)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem == newItem
    }
}