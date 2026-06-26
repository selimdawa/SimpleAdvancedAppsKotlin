package com.flatcode.simpleadvancedapps.todoNote.ui.notes

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentNotesBinding
import com.flatcode.simpleadvancedapps.todoNote.data.Notes
import com.flatcode.simpleadvancedapps.todoNote.data.SortOrder
import com.flatcode.simpleadvancedapps.todoNote.util.exhaustive
import com.flatcode.simpleadvancedapps.todoNote.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment(), NotesAdapter.OnItemClickListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesAdapter = NotesAdapter(this)

        binding.apply {
            notesRec.apply {
                adapter = notesAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }

            floatAddButton.setOnClickListener { viewModel.onAddNewNoteClick() }

            setFragmentResultListener("note_add_edit_request") { _, bundle ->
                val result = bundle.getInt("note_add_edit_result")
                viewModel.onAddEditNoteResult(result)
            }
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            notesAdapter.differ.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noteEvent.collect { event ->
                when (event) {
                    is NotesViewModel.NotesEvent.NavigateToAddScreen -> {
                        val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                            title = "New Note", Note = null
                        )
                        findNavController().navigate(action)
                    }

                    is NotesViewModel.NotesEvent.NavigateToEditNoteScreen -> {
                        val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                            title = "Edit Note", Note = event.note
                        )
                        findNavController().navigate(action)
                    }

                    is NotesViewModel.NotesEvent.ShowUndoDeleteNoteMessage -> {
                        Snackbar.make(requireView(), "Note Deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClick(event.note)
                            }.show()
                    }

                    is NotesViewModel.NotesEvent.ShowNoteSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }

                    is NotesViewModel.NotesEvent.NavigateToDeleteAllScreen -> {
                        val action = NotesFragmentDirections.actionGlobalDeleteAllNotes()
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
                menuInflater.inflate(R.menu.menu_fragment_notes, menu)

                val searchItem = menu.findItem(R.id.action_search_notes)
                searchView = searchItem.actionView as SearchView

                searchView.onQueryTextChanged { viewModel.searchQuery.value = it }

                val pendingQuery = viewModel.searchQuery.value
                if (!pendingQuery.isNullOrEmpty()) {
                    searchItem.expandActionView()
                    searchView.setQuery(pendingQuery, false)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete_all_notes -> {
                        viewModel.deleteAllNotes()
                        true
                    }

                    R.id.action_sort_byname_notes -> {
                        viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                        true
                    }

                    R.id.action_sort_bydatecreated_notes -> {
                        viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onItemClick(note: Notes) {
        viewModel.onNoteSelected(note)
    }

    override fun onDeleteNoteClick(note: Notes) {
        viewModel.deleteNote(note)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::searchView.isInitialized) {
            searchView.setOnQueryTextListener(null)
        }
        _binding = null
    }
}