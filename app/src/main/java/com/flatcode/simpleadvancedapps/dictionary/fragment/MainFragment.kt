package com.littleapp.dictionary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentMainDictionaryBinding
import com.flatcode.simpleadvancedapps.dictionary.utils.UiState
import com.flatcode.simpleadvancedapps.dictionary.viewmodel.MainViewModel
import com.flatcode.simpleadvancedapps.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainDictionaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph_dictionary)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.nameSpace.text = Constants.DICTIONARY
    }

    private fun setupListeners() {
        binding.findButton.setOnClickListener {
            val word = binding.searchEditText.text.toString()
            if (word.isNotEmpty()) {
                viewModel.searchWord(word)
            } else {
                Toast.makeText(requireContext(), "Please enter a word", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        handleUiState(state)
                    }
                }
                launch {
                    viewModel.navigationEvent.collect {
                        findNavController().navigate(R.id.action_mainFragment_to_definitionWordFragment)
                    }
                }
            }
        }
    }

    private fun handleUiState(state: UiState<String>) {
        when (state) {
            is UiState.Loading -> {
                binding.findButton.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
            }

            is UiState.Success -> {
                binding.findButton.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }

            is UiState.Error -> {
                binding.findButton.isEnabled = true
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }

            is UiState.Idle -> {
                binding.findButton.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}