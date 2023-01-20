package com.example.roomexample01.Model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomexample01.Model.TaskEntity

// SE DECLARAN METODOS PARA  OPERAR CON LA BASE DE DATOS CREAR, LEER, ESCRIBIR, UPDATE, BORRAR
// NUMERO 2
@Dao
interface TaskDao {

    //inserta una tarea OBJETO DEL LA ENTIDAD  , ESTRATEGIA DE CONFLICTO REPETICIÓN EJ ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend infix fun insertTask(task: TaskEntity)

    //inserta listado de tareas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTask(listTask: List<TaskEntity>)

    //borrar una tarea
    @Delete
    suspend fun deleteTask(task: TaskEntity)

    // actualzar datos
    @Update
    suspend fun updateTask(task: TaskEntity)



    // Borra todos los datos
    @Query("DELETE FROM  TASK_TABLE")
    fun deleteAll()

    // traer todos los elementos de la tabla
    @Query("SELECT * FROM  TASK_TABLE")
    //fun getAllTask(): List<TaskEntity>
    fun getAllTask(): LiveData<List<TaskEntity>>

    // traer por titulo
    @Query("SELECT * FROM  TASK_TABLE WHERE title=:title Limit 1")
    fun getTaskByTitle(title: String):LiveData<TaskEntity>


    // traer una tarea por Id
    @Query("SELECT * FROM TASK_TABLE WHERE id =:id")
    fun getTaskByID(id:Int): LiveData<TaskEntity>







}