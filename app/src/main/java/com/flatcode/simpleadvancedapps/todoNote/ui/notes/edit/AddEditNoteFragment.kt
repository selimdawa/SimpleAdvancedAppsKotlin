package com.flatcode.simpleadvancedapps.todoNote.ui.notes.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.flatcode.simpleadvancedapps.databinding.FragmentAddEditNoteBinding
import com.flatcode.simpleadvancedapps.todoNote.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            noteTitleEditText.setText(viewModel.noteTitle)
            noteContentEditText.setText(viewModel.noteContent)
            currentDateNote.isVisible = viewModel.note != null
            currentDateNote.text = "Created:  ${viewModel.note?.createdDateFormatted}"

            noteTitleEditText.addTextChangedListener { viewModel.noteTitle = it.toString() }
            noteContentEditText.addTextChangedListener { viewModel.noteContent = it.toString() }
            noteAddEditFloatBttn.setOnClickListener { viewModel.onSaveClick() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addEditNoteEvent.collect { event ->
                when (event) {
                    is AddEditNoteViewModel.AddEditNoteEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }

                    is AddEditNoteViewModel.AddEditNoteEvent.NavigateWithResult -> {
                        binding.noteTitleEditText.clearFocus()

                        val resultBundle = Bundle().apply {
                            putInt("note_add_edit_request", event.result)
                        }
                        setFragmentResult("note_add_edit_request", resultBundle)

                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}