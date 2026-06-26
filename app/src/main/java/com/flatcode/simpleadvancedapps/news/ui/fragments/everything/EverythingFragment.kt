package com.flatcode.simpleadvancedapps.news.ui.fragments.everything

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentEverythingBinding
import com.flatcode.simpleadvancedapps.news.base.BaseFragment
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.ui.adapters.EverythingAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EverythingFragment : BaseFragment<FragmentEverythingBinding, EverythingViewModel>(R.layout.fragment_everything) {

    private var _binding: FragmentEverythingBinding? = null
    override val binding get() = _binding!!
    override val viewModel: EverythingViewModel by viewModels()
    private val adapter = EverythingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEverythingBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupSubscribes()
    }

    override fun initialize() {
        setupRecyclerView()
    }

    override fun setupSubscribes() {
        subscribesEverything()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun subscribesEverything() {
        viewModel.fetchEverything().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Timber.e(resource.message.orEmpty())
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    resource.data?.articles?.let { articles ->
                        adapter.submitList(articles)
                    }
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}