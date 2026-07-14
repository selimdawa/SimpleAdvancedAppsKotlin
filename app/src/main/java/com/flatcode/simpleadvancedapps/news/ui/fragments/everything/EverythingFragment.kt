package com.flatcode.simpleadvancedapps.news.ui.fragments.everything

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentEverythingBinding
import com.flatcode.simpleadvancedapps.news.base.BaseFragment
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.common.viewBinding
import com.flatcode.simpleadvancedapps.news.ui.adapters.EverythingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EverythingFragment :
    BaseFragment<FragmentEverythingBinding, EverythingViewModel>(R.layout.fragment_everything) {

    override val binding by viewBinding(FragmentEverythingBinding::bind)
    override val viewModel: EverythingViewModel by hiltNavGraphViewModels(R.id.nav_graph_news)
    private val adapter = EverythingAdapter()

    override fun initialize() {
        binding.recyclerView.adapter = adapter
    }

    override fun setupSubscribes() {
        subscribeToEverything()
    }

    private fun subscribeToEverything() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.everythingState.collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Timber.e(resource.message)
                        }

                        is Resource.Loading -> {
                            // Handle loading state if needed
                        }

                        is Resource.Success -> {
                            adapter.submitList(resource.data.articles)
                        }
                    }
                }
            }
        }
    }
}