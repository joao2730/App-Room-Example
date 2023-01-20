package com.example.roomexample01

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample01.ViewModel.TaskViewModel
import com.example.roomexample01.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding

    // agregar View Model
    private val viewModel: TaskViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // referencia Adapter
        val adapter = TaskAdapter()
        binding.rvTask.adapter = adapter
        binding.rvTask.layoutManager = LinearLayoutManager(context)
        // añade un linea
        binding.rvTask.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


        // recorrer la base de datos Y OBSERVA SI HAY CAMBIOS
        viewModel.allTask.observe(viewLifecycleOwner, Observer {
            // hacemos update para actualizar nuestro adaptador
            it?.let {
                adapter.update(it)
            }

        })

        /*   val newTask = TaskEntity( title = "BD",
            descripcion = "Base Prueba",
            date = "07/12/2022",
            priority = 2,
            state = true)
        viewModel.insertTask(newTask)*/

        binding.fab2.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        adapter.selectedItem().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("ITEM SELECTED", it.title)
                // LLLAMAMO AL METODO SELECTED Y LE ENTREGAMOS EL OBJETO
                viewModel.selected(it)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        })
    }






}


