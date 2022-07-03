package com.julia.mvvmtodo.banco

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TaskEntry::class), version = 1, exportSchema = false)
abstract class TaskBanco: RoomDatabase() {
 abstract fun taskDao(): TaskDao
 companion object{
  @Volatile
  private var INSTANCE: TaskBanco? = null
  fun getDataBase(context: Context):TaskBanco{
   synchronized(this){
    var instance = INSTANCE
    if(instance == null){
     instance = Room.databaseBuilder(
      context.applicationContext, TaskBanco::class.java,"task_database"
     ).fallbackToDestructiveMigration().build()
     INSTANCE = instance
    }
    return instance
   }
  }
 }

}