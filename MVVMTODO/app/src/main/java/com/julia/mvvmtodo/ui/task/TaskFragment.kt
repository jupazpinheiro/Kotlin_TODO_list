package com.julia.mvvmtodo.ui.task

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.julia.mvvmtodo.R
import com.julia.mvvmtodo.databinding.FragmentTaskBinding
import com.julia.mvvmtodo.ui.MainActivity
import com.julia.mvvmtodo.viewmodel.TaskViewModel


class TaskFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    var info =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaskBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.getAllTasksOpa().observe(viewLifecycleOwner, Observer { task -> task.forEach {
            info += "Tarefa ${it.title} \n Prioridade ${it.priority} \n\n";
            println("${it.title}")
        } })


        adapter = TaskAdapter(TaskClickListener {
                taskEntry ->  findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToUpdateFragment(taskEntry))})
        viewModel.getAllTasks.observe(viewLifecycleOwner){
            adapter.submitList(it);
        }
        binding.apply {
            binding.recycleView.adapter = adapter
            floatingActionButton.setOnClickListener{
                findNavController().navigate(R.id.action_taskFragment_to_addFragment)
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntry = adapter.currentList[position]
                viewModel.delete(taskEntry)

                Snackbar.make(binding.root,"Apagado", Snackbar.LENGTH_LONG).apply {
                    setAction("Desfeito"){
                        viewModel.insert(taskEntry)
                    }
                    show()
                }
            }

        }).attachToRecyclerView(binding.recycleView)

        setHasOptionsMenu(true)

        hideTeclado(requireActivity())
        return binding.root
    }

    private fun hideTeclado(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu,menu)
        val searchItem = menu.findItem(R.id.procurar)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText !=null){
                    runQuery(newText)
                }
                return true
            }

        })
    }
    fun runQuery(query: String){
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { task -> adapter.submitList(task) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.prioridade -> viewModel.getAllPriorityTasks.observe(viewLifecycleOwner, Observer {
                    task -> adapter.submitList(task)

            })
            R.id.delet_all -> deleteAllItem()
            R.id.share -> startShareIntent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, info )
        //startActivity(shareIntent)

        // Verify that the intent will resolve to an activity
        val packageManager = requireActivity().packageManager
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        }


    }

    private fun deleteAllItem() {
        AlertDialog.Builder(requireContext()).setTitle("Apagar tudo").setMessage("Certeza?")
            .setPositiveButton("Yes"){dialog, _->viewModel.deleteAll()
            dialog.dismiss()}.setNegativeButton("No"){dialog, _->dialog.dismiss()}.create().show()

    }
}