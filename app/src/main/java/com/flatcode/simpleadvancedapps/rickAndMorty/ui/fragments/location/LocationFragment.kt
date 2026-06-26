package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.location

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentLocationBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseFragment
import com.flatcode.simpleadvancedapps.rickAndMorty.common.Resource
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.adapters.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LocationFragment :
    BaseFragment<FragmentLocationBinding, LocationViewModel>(R.layout.fragment_location) {

    private var _binding: FragmentLocationBinding? = null
    override val binding get() = _binding!!

    override val viewModel: LocationViewModel by viewModels()
    private val adapter = LocationAdapter(arrayListOf())
    private var count = 1

    override fun initialize() {
        _binding = FragmentLocationBinding.bind(requireView())
        setupRecyclerView()
    }

    override fun setupSubscribe() {
        subscribeToLocation()
    }

    private fun setupRecyclerView() {
        binding.rvLocation.adapter = adapter
        binding.rvLocation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(requireActivity(), "Last", Toast.LENGTH_LONG).show()
                    ++count
                    subscribeToLocation()
                }
            }
        })
    }

    private fun subscribeToLocation() {
        lifecycleScope.launch {
            viewModel.fetchLocation(page = count).collect { resource ->
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