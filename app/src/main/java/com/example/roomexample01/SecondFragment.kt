package com.example.roomexample01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.roomexample01.Model.TaskEntity
import com.example.roomexample01.ViewModel.TaskViewModel
import com.example.roomexample01.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var _binding: FragmentSecondBinding
    // agregar View Model
    private val viewModel: TaskViewModel by activityViewModels()
    private var idTask: Int=0
    private var taskSeleted : TaskEntity? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recibe la entidad seleccionada
        viewModel.selectedItem().observe(viewLifecycleOwner, Observer {
            it.let {
                if (it != null) {
                    binding.etTitle.setText(it.title)
                    binding.etDate.setText(it.date)
                    binding.etDescription.setText(it.descripcion)
                    binding.etPriority.setText(it.priority.toString())
                    binding.cbStatenew.isChecked = it.state
                  // cargamos en la variable idTask el id recibido
                    idTask = it.id
                    // la tarea seleccionado es igual a it
                    taskSeleted = it
                }
            }

        })

        // BUTTON PARA GUARDAR LE CARGAMOS LA FUNCIÓN GUARDAR
        binding.btnsave.setOnClickListener{

            saveData()
            viewModel.selected(null)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }


    // MÉTODO PARA GUARDAR NO OLVIDAR CARGAR LA FUNCIÓN AL BUTTON
    private fun saveData(){
        val title = binding.etTitle.text.toString()
        val descripcion = binding.etDescription.text.toString()
        val date = binding.etDate.text.toString()
        val priority = binding.etPriority.text.toString().toInt()
        val state= binding.cbStatenew.isChecked

        // SE VALIDAD ALGUNOS CAMPOS
       if(title.isEmpty() && descripcion.isEmpty() && date.isEmpty()) {
           Toast.makeText(context," Debe añadir los datos",Toast.LENGTH_LONG).show()
       }
        else {
            // SI NO HAY NADA CREA UNA NUEVA ACTIVIDAD
            // SI HAY ALGO EN LA BASE DE DATOS LA ACTUALIZA
            if(idTask==0) {
                val newTask = TaskEntity( title = title,
                    descripcion = descripcion,
                    date = date,
                    priority = priority,
                    state = state)
                viewModel.insertTask(newTask)
                Toast.makeText(context," DATOS GUARDADOS CORRECTAMENTE",Toast.LENGTH_LONG).show()

            }else{
                val newTask1 = TaskEntity(
                    // debemos incluir el id que viene del
                    // observer para saber cual actualizar
                    id= idTask,
                    title = title,
                    descripcion = descripcion,
                    date = date,
                    priority = priority,
                    state = state)
                viewModel.updateTask(newTask1)
                Toast.makeText(context," DATOS ACTUALIZADOS",Toast.LENGTH_LONG).show()
            }
        }
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}