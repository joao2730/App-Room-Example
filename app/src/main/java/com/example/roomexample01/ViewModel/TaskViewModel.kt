package com.example.roomexample01.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomexample01.Model.TaskDataBase
import com.example.roomexample01.Model.TaskEntity
import com.example.roomexample01.Model.TaskRepository
import kotlinx.coroutines.launch


class TaskViewModel( application: Application) : AndroidViewModel(application) {

    // CONEXIÓN CON EL RESPOSITORIO
    private val repository: TaskRepository

    // Live Data que expone la info del modelo
    val allTask: LiveData<List<TaskEntity>>

    init {

        // Necesito La instancia del repositorio
        // ESTAN LAS 3 CLASE CONECTADAS
        val taskDao = TaskDataBase.getDatabase(application).getTaskDao()
        repository = TaskRepository(taskDao)
        allTask = repository.listAllTask

    }

    // El viewmodel Scope trabaja con las coroutines  hace que se ejecute el proceso en el hilo secundario
    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        repository.insertTask(task)
    }
    // manejar la actualización de los datos

    fun updateTask(task: TaskEntity) = viewModelScope.launch {
        repository.updateTask(task)
    }


    private var selectedTask: MutableLiveData<TaskEntity?> = MutableLiveData()

    // FUNCION PARA SELECCIONAR que puede ser null
    fun selected(task: TaskEntity?) {
        // GUARDAR LA TAREA SELECCIONADA
        selectedTask.value = task
    }

    // FUNCION PARA RECIBIR EL OBJETO SELECCIONADO
    fun selectedItem(): LiveData<TaskEntity?> = selectedTask
}