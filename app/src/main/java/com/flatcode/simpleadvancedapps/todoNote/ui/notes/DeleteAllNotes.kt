package com.flatcode.simpleadvancedapps.todoNote.ui.notes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.DialogCustomDeleteNotesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllNotes : DialogFragment() {

    private var _binding: DialogCustomDeleteNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCustomDeleteNotesBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()

        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }

            btnDelete.setOnClickListener {
                viewModel.onDeleteAllNotesConfirmed()
                dismiss()
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}