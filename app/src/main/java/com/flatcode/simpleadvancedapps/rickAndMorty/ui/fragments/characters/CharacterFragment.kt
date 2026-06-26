package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.characters

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentCharacterBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseFragment
import com.flatcode.simpleadvancedapps.rickAndMorty.common.Resource
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CharacterFragment :
    BaseFragment<FragmentCharacterBinding, CharacterViewModel>(R.layout.fragment_character) {

    private var _binding: FragmentCharacterBinding? = null
    override val binding get() = _binding!!

    override val viewModel: CharacterViewModel by viewModels()
    private var adapter: CharacterAdapter? = null
    private var count = 1

    override fun initialize() {
        _binding = FragmentCharacterBinding.bind(requireView())
        setupRecyclerView()
    }

    override fun setupSubscribe() {
        subscribeToCharacter()
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter(context, arrayListOf())
        binding.rvCharacter.adapter = adapter
        binding.rvCharacter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(requireActivity(), "Last", Toast.LENGTH_LONG).show()
                    ++count
                    subscribeToCharacter()
                }
            }
        })
    }

    private fun subscribeToCharacter() {
        lifecycleScope.launch {
            viewModel.fetchCharacters(page = count).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Timber.e(resource.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        resource.data?.let { response -> adapter?.addNewItems(response.results) }
                        Timber.e(resource.data?.results.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}