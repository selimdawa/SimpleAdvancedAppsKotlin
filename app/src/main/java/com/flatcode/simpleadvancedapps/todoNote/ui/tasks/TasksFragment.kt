package com.flatcode.simpleadvancedapps.todoNote.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentTasksBinding
import com.flatcode.simpleadvancedapps.todoNote.data.SortOrder
import com.flatcode.simpleadvancedapps.todoNote.data.Task
import com.flatcode.simpleadvancedapps.todoNote.util.exhaustive
import com.flatcode.simpleadvancedapps.todoNote.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment(), TaskAdapter.OnItemClickListener {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by navGraphViewModels(R.id.nav_graph_todo) {
        defaultViewModelProviderFactory
    }

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskAdapter = TaskAdapter(this)

        binding.apply {
            tasksRec.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            floatAddButton.setOnClickListener { viewModel.onAddNewTaskClick() }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = taskAdapter.currentList[position]
                        viewModel.onTaskSwiped(task)
                    }
                }
            }).attachToRecyclerView(tasksRec)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskEvent.collect { event ->
                when (event) {
                    is TasksViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.msg_task_deleted),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction(getString(R.string.action_undo)) {
                                viewModel.onUndoDeleteClick(event.task)
                            }.show()
                    }

                    is TasksViewModel.TasksEvent.NavigateToAddScreen -> {
                        val action = TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                            task = null, title = getString(R.string.title_new_task)
                        )
                        findNavController().navigate(action)
                    }

                    is TasksViewModel.TasksEvent.NavigateToEditTaskScreen -> {
                        val action = TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                            task = event.task, title = getString(R.string.title_edit_task)
                        )
                        findNavController().navigate(action)
                    }

                    is TasksViewModel.TasksEvent.ShowTaskSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }

                    is TasksViewModel.TasksEvent.NavigateToDeleteAllCompletedTasksScreen -> {
                        val action = TasksFragmentDirections.actionGlobalDeleteAllCompletedTasksDialogFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_tasks, menu)

                val searchItem = menu.findItem(R.id.action_search)
                searchView = searchItem.actionView as SearchView

                searchView.onQueryTextChanged { viewModel.searchQuery.value = it }

                val pendingQuery = viewModel.searchQuery.value
                if (!pendingQuery.isNullOrEmpty()) {
                    searchItem.expandActionView()
                    searchView.setQuery(pendingQuery, false)
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    menu.findItem(R.id.action_hide_cpmpleted_items).isChecked =
                        viewModel.preferencesFlow.first().hideCompleted
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort_byname -> {
                        viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                        true
                    }

                    R.id.action_sort_bydatecreated -> {
                        viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                        true
                    }

                    R.id.action_hide_cpmpleted_items -> {
                        menuItem.isChecked = !menuItem.isChecked
                        viewModel.onHideCompletedClick(menuItem.isChecked)
                        true
                    }

                    R.id.action_delete_all_comp_tasks -> {
                        val action = TasksFragmentDirections.actionGlobalDeleteAllCompletedTasksDialogFragment()
                        findNavController().navigate(action)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onItemClick(task: Task) {
        viewModel.onTaskSelected(task)
    }

    override fun onChecBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::searchView.isInitialized) {
            searchView.setOnQueryTextListener(null)
        }
        _binding = null
    }
}