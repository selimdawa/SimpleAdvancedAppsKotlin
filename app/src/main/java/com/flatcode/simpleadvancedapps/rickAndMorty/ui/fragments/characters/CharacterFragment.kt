package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.characters

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
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

    override val binding by viewBinding(FragmentCharacterBinding::bind)
    override val viewModel: CharacterViewModel by viewModels()
    private var adapter: CharacterAdapter? = null
    private var count = 1

    override fun initialize() {
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
            viewModel.fetchCharacters(page = count).collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.e(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { it1 -> adapter!!.addNewItems(it1.results) }
                        Timber.e(it.data?.results.toString())
                    }
                }
            }
        }
    }
}