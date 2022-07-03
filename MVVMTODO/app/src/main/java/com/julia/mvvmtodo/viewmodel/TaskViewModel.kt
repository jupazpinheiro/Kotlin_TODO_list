package com.julia.mvvmtodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.julia.mvvmtodo.banco.TaskBanco
import com.julia.mvvmtodo.banco.TaskEntry
import com.julia.mvvmtodo.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    private val taskDao = TaskBanco.getDataBase(application).taskDao()
    private val repository : TaskRepository
    val getAllTasks : LiveData<List<TaskEntry>>
    val getAllPriorityTasks : LiveData<List<TaskEntry>>
    init{
        repository= TaskRepository(taskDao)
        getAllTasks = repository.getAllTasks()
        getAllPriorityTasks = repository.getAllPriorityTasks()
    }

    fun insert(taskEntry: TaskEntry){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(taskEntry)
        }
    }
    fun delete(taskEntry: TaskEntry){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(taskEntry)
        }
    }
    fun update(taskEntry: TaskEntry){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(taskEntry)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<TaskEntry>>{
        return repository.searchDatabase(searchQuery)
    }

    fun getAllTasksOpa(): LiveData<List<TaskEntry>>{
        return repository.getAllTasks()
    }
}