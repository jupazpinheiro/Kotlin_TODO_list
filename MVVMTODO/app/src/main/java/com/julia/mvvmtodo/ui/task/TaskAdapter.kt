package com.julia.mvvmtodo.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.julia.mvvmtodo.banco.TaskEntry
import com.julia.mvvmtodo.databinding.LinhaLayoutBinding

class TaskAdapter (val clickListener: TaskClickListener) : androidx.recyclerview.widget.ListAdapter <TaskEntry,TaskAdapter.ViewHolder>(TaskDiffCallBack) {

    companion object TaskDiffCallBack : DiffUtil.ItemCallback<TaskEntry>(){
        override fun areItemsTheSame(oldItem: TaskEntry, newItem: TaskEntry) = oldItem.id ==newItem.id
        override fun areContentsTheSame(oldItem: TaskEntry, newItem: TaskEntry)= oldItem ==newItem

    }

    class ViewHolder(val binding: LinhaLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(taskEntry: TaskEntry, clickListener: TaskClickListener){
            binding.taskEntry = taskEntry
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LinhaLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,clickListener)
    }


}
class TaskClickListener(val clickListener: (taskEntry: TaskEntry) -> Unit){
    fun onClick(taskEntry: TaskEntry) = clickListener(taskEntry)
}