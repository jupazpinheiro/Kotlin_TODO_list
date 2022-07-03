package com.julia.mvvmtodo.repository

import androidx.lifecycle.LiveData
import com.julia.mvvmtodo.banco.TaskDao
import com.julia.mvvmtodo.banco.TaskEntry

class TaskRepository(val taskDao: TaskDao) {
    fun insert(taskEntry: TaskEntry) = taskDao.insert(taskEntry)
    fun updateData(taskEntry: TaskEntry) = taskDao.update(taskEntry)
    fun deleteItem(taskEntry: TaskEntry) = taskDao.delete(taskEntry)
    fun deleteAll(){taskDao.deleteAll()}
    fun getAllTasks(): LiveData<List<TaskEntry>> = taskDao.getAllTasks()
    fun getAllPriorityTasks():LiveData<List<TaskEntry>> = taskDao.getAllPriorityTasks()

    fun searchDatabase(searchQuery: String): LiveData<List<TaskEntry>>{
        return taskDao.searchDatabase(searchQuery)
    }
}