package com.julia.mvvmtodo.ui.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.julia.mvvmtodo.R
import com.julia.mvvmtodo.banco.TaskEntry
import com.julia.mvvmtodo.databinding.FragmentUpdateBinding
import com.julia.mvvmtodo.viewmodel.TaskViewModel

class UpdateFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpdateBinding.inflate(inflater)
        val args = UpdateFragmentArgs.fromBundle(requireArguments())
        binding.apply {
            upEdtTarefa.setText(args.taskEntry.title)
            upSpinner.setSelection(args.taskEntry.priority)
            btnUp.setOnClickListener{
                if (TextUtils.isEmpty(upEdtTarefa.text)){
                    Toast.makeText(requireContext(), "Vazio", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val task_str = upEdtTarefa.text
                val priority = upSpinner.selectedItemPosition
                val taskEntry = TaskEntry(
                    args.taskEntry.id, task_str.toString(),priority,args.taskEntry.timestamp
                )
                viewModel.update(taskEntry)
                Toast.makeText(requireContext(),"Atualizado",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_taskFragment)
            }
        }
        return binding.root
    }
}