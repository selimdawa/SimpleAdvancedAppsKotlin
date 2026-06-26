package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.episode

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
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

    private var _binding: FragmentEpisodeBinding? = null
    override val binding get() = _binding!!

    override val viewModel: EpisodeViewModel by viewModels()
    private val adapter = EpisodeAdapter(arrayListOf())
    private var count = 1

    override fun initialize() {
        _binding = FragmentEpisodeBinding.bind(requireView())
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
            viewModel.fetchEpisode(page = count).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Timber.e(resource.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        resource.data?.let { response -> adapter.addNewItems(response.results) }
                        Timber.e(resource.data?.results.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}