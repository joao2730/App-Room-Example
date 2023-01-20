package com.example.roomexample01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample01.Model.TaskEntity
import com.example.roomexample01.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskVH>() {

    // lista de los datos
    private var mlistTaskEntity = listOf<TaskEntity>()

    // seleccionar un elemento
    // variable encargada de obtener los datos, ya sea de internet, caché o base de datos.
    // errorMessageLiveData = MutableLiveData<String>().
    // Se trata de un LiveData que informará a la vista cuando haya un error al obtener los datos del repositorio
    private val selectedTaskEntity = MutableLiveData<TaskEntity>()

    // devuele el elemento
    fun selectedItem(): LiveData<TaskEntity> = selectedTaskEntity

    // RECIBE UN LISTADO DE TAREAS
    fun update(listTaskEntity: List<TaskEntity>) {
        mlistTaskEntity = listTaskEntity
        notifyDataSetChanged()
    }


    // SE COMUNICA CON EL XML QUE TENDRA LA VISTA DEL RECYCLERVIEW
    // puedo tener acceso dentro de la clase
    inner class TaskVH(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        // implemento el Clik
        View.OnClickListener {

        fun bind(task: TaskEntity) {

            binding.tvTitle.text = task.title
            binding.tvDescription.text = task.descripcion
            binding.tvDate.text = task.date
            binding.cbState.isChecked = task.state
            binding.tvPrioridad.text= task.priority.toString()
            // Activar el elemento click dentro de la vista
            itemView.setOnClickListener(this)


        }

        override fun onClick(v: View?) {
            // no tiene un select
            selectedTaskEntity.value = mlistTaskEntity[adapterPosition]


        }
    }

    // infla la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        return TaskVH(TaskItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    // Entrega una posicion y une con el objeto por posicion
    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val taskEntity = mlistTaskEntity[position]
        holder.bind(taskEntity)
    }

    // Muestra total de los elementos
    override fun getItemCount(): Int = mlistTaskEntity.size

}


