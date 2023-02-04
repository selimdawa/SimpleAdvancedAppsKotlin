package com.flatcode.simpleadvancedapps.news.ui.fragments.toparticles

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentTopArticlesBinding
import com.flatcode.simpleadvancedapps.news.base.BaseFragment
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.ui.adapters.TopArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TopArticlesFragment :
    BaseFragment<FragmentTopArticlesBinding, TopArticlesViewModel>(R.layout.fragment_top_articles) {

    override val binding by viewBinding(FragmentTopArticlesBinding::bind)
    override val viewModel: TopArticlesViewModel by viewModels()
    private val adapter = TopArticlesAdapter()

    override fun initialize() {
        setupRecyclerView()
    }

    override fun setupSubscribes() {
        subscribesTopArticles()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun subscribesTopArticles() {
        viewModel.fetchTopArticles().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Timber.e(it.message.toString())
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(it.data?.articles)
                }
                else -> {}
            }
        }
    }
}