package com.flatcode.simpleadvancedapps.rickAndMorty.ui.locations

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseFragment
import com.flatcode.simpleadvancedapps.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding, LocationViewModel>(
    FragmentLocationBinding::inflate,
) {
    override val viewModel: LocationViewModel by hiltNavGraphViewModels(R.id.nav_graph_rick)
    private val adapter = LocationAdapter()
    private var count = 1
    private var isLoading = false

    override fun initialize() {
        binding.rvLocation.adapter = adapter
        binding.rvLocation.onScrollEnd(isLoading) {
            viewModel.fetchLocations(++count).collectResource { adapter.submitList(it) }
        }
    }

    override fun setupSubscribe() {
        viewModel.fetchLocations(count).collectResource(
            onLoading = { isLoading = it },
            onError = { Timber.e(it) }
        ) { adapter.submitList(it) }
    }
}