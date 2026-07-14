package com.flatcode.simpleadvancedapps.news.ui.fragments.toparticles

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentTopArticlesBinding
import com.flatcode.simpleadvancedapps.news.base.BaseFragment
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.common.viewBinding
import com.flatcode.simpleadvancedapps.news.ui.adapters.TopArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TopArticlesFragment :
    BaseFragment<FragmentTopArticlesBinding, TopArticlesViewModel>(R.layout.fragment_top_articles) {

    override val binding by viewBinding(FragmentTopArticlesBinding::bind)
    override val viewModel: TopArticlesViewModel by hiltNavGraphViewModels(R.id.nav_graph_news)
    private val adapter = TopArticlesAdapter()

    override fun initialize() {
        binding.recyclerView.adapter = adapter
    }

    override fun setupSubscribes() {
        subscribeToTopArticles()
    }

    private fun subscribeToTopArticles() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topArticlesState.collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Timber.e(resource.message)
                        }

                        is Resource.Loading -> {
                            // Handle loading state
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