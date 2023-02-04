package com.flatcode.simpleadvancedapps.news.ui.fragments.everything

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentEverythingBinding
import com.flatcode.simpleadvancedapps.news.base.BaseFragment
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.ui.adapters.EverythingAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EverythingFragment :
    BaseFragment<FragmentEverythingBinding, EverythingViewModel>(R.layout.fragment_everything) {

    override val binding by viewBinding(FragmentEverythingBinding::bind)
    override val viewModel: EverythingViewModel by viewModels()
    private val adapter = EverythingAdapter()

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
        viewModel.fetchEverything().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Timber.e(it.message.toString())
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(it.data!!.articles)
                }
                else -> {}
            }
        }
    }
}