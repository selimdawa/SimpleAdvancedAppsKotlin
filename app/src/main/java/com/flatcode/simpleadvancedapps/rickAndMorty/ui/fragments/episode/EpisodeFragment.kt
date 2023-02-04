package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.episode

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentEpisodeBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseFragment
import com.flatcode.simpleadvancedapps.rickAndMorty.common.Resource
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters.EpisodeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EpisodeFragment :
    BaseFragment<FragmentEpisodeBinding, EpisodeViewModel>(R.layout.fragment_episode) {

    override val binding by viewBinding(FragmentEpisodeBinding::bind)
    override val viewModel: EpisodeViewModel by viewModels()
    private val adapter = EpisodeAdapter(arrayListOf())
    private var count = 1

    override fun initialize() {
        setupRecyclerView()
    }

    override fun setupSubscribe() {
        subscribeToEpisode()
    }

    private fun setupRecyclerView() {
        binding.rvEpisode.adapter = adapter
        binding.rvEpisode.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(requireActivity(), "Last", Toast.LENGTH_LONG).show()
                    ++count
                    subscribeToEpisode()
                }
            }
        })
    }

    private fun subscribeToEpisode() {
        lifecycleScope.launch {
            viewModel.fetchEpisode(page = count).collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.e(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { it1 -> adapter.addNewItems(it1.results) }
                        Timber.e(it.data?.results.toString())
                    }
                }
            }
        }
    }
}