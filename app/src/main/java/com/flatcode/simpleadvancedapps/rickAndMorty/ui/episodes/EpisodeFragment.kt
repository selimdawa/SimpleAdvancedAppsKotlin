package com.flatcode.simpleadvancedapps.rickAndMorty.ui.episodes

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseFragment
import com.flatcode.simpleadvancedapps.databinding.FragmentEpisodeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EpisodeFragment : BaseFragment<FragmentEpisodeBinding, EpisodeViewModel>(
    FragmentEpisodeBinding::inflate,
) {
    override val viewModel: EpisodeViewModel by hiltNavGraphViewModels(R.id.nav_graph_rick)
    private val adapter = EpisodeAdapter()
    private var count = 1
    private var isLoading = false

    override fun initialize() {
        binding.rvEpisode.adapter = adapter
        binding.rvEpisode.onScrollEnd(isLoading) {
            viewModel.fetchEpisodes(++count).collectResource { adapter.submitList(it) }
        }
    }

    override fun setupSubscribe() {
        viewModel.fetchEpisodes(count).collectResource(
            onLoading = { isLoading = it },
            onError = { Timber.e(it) }
        ) { adapter.submitList(it) }
    }
}