package com.julia.mvvmtodo.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.julia.mvvmtodo.R
import com.julia.mvvmtodo.banco.TaskEntry
import com.julia.mvvmtodo.databinding.FragmentAddBinding
import com.julia.mvvmtodo.viewmodel.TaskViewModel

class AddFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddBinding.inflate(inflater)
        val myAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.prioridades)

        )

        binding.apply {
            spinner.adapter = myAdapter
            btnAdicionar.setOnClickListener {
                if (TextUtils.isEmpty((edtTarefa.text))) {
                    Toast.makeText(requireContext(), "Sem tarefas", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val title_str = edtTarefa.text.toString()
                val priority = spinner.selectedItemPosition
                val taskEntry = TaskEntry(
                    0,
                    title_str,
                    priority,
                    System.currentTimeMillis()
                )
                viewModel.insert(taskEntry)
                Toast.makeText(requireContext(),"Tarefa adicionada",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_taskFragment)
            }
        }

        return binding.root

    }
}