package com.flatcode.simpleadvancedapps.rickAndMorty.ui.characters

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentCharacterBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding, CharacterViewModel>(
    FragmentCharacterBinding::inflate,
) {
    override val viewModel: CharacterViewModel by hiltNavGraphViewModels(R.id.nav_graph_rick)
    private val adapter = CharacterAdapter()
    private var count = 1
    private var isLoading = false

    override fun initialize() {
        binding.rvCharacter.adapter = adapter
        binding.rvCharacter.onScrollEnd(isLoading) {
            viewModel.fetchCharacters(++count).collectResource { adapter.submitList(it) }
        }
    }

    override fun setupSubscribe() {
        viewModel.fetchCharacters(count).collectResource(
            onLoading = { isLoading = it },
            onError = { Timber.e(it) }
        ) { adapter.submitList(it) }
    }
}