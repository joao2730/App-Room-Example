package com.example.roomexample01.Model

import androidx.lifecycle.LiveData
import com.example.roomexample01.Model.TaskDao
import com.example.roomexample01.Model.TaskEntity

// RESPONSABILIDAD DE EXPONER LOS DATOS PARA VIEWMODEL QUE PROVIENE DE LA BD

class TaskRepository ( private  val taskDao: TaskDao){

    // Este value va a contener toda la información de la base de datos

    val listAllTask : LiveData<List<TaskEntity>> = taskDao.getAllTask()

   suspend fun insertTask( task: TaskEntity){
        taskDao.insertTask(task)
    }


    // fucion que actualiza
    suspend fun updateTask(task: TaskEntity){
        taskDao.updateTask(task)

    }
   suspend fun deleteTask(task: TaskEntity){
        taskDao.deleteTask(task)
    }




}