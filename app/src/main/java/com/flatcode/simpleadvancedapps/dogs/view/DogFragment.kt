package com.flatcode.simpleadvancedapps.dogs.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentDogBinding
import com.flatcode.simpleadvancedapps.dogs.viewmodel.DogUiState
import com.flatcode.simpleadvancedapps.dogs.viewmodel.DogViewModel
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.dogs.utils.toast
import com.flatcode.simpleadvancedapps.dogs.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DogFragment : Fragment(), AdapterView.OnItemClickListener {

    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DogViewModel by hiltNavGraphViewModels(R.id.nav_graph_dogs)
    private val dogAdapter = DogAdapter()
    private var lastSelectedBreed: String? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingBreedsList()

        with(binding) {
            recyclerViewDog.adapter = dogAdapter
            recyclerViewDog.layoutManager = LinearLayoutManager(requireContext())
            toolbar.nameSpace.text = DATA.DOGS
        }

        observeViewModel()
        registerNetworkCallback()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.breedsList.collect { newList ->
                        if (newList.isNotEmpty()) {
                            val adapter =
                                ArrayAdapter(requireContext(), R.layout.list_breeds, newList)
                            with(binding.autoCompleteTextView) {
                                setAdapter(adapter)
                                onItemClickListener = this@DogFragment
                            }
                        }
                    }
                }

                launch {
                    viewModel.uiState.collect { state ->
                        handleUiState(state)
                    }
                }
            }
        }
    }

    private fun handleUiState(state: DogUiState) {
        with(binding) {
            statusImageError.visibleIf(state is DogUiState.Error)
            recyclerViewDog.visibleIf(state !is DogUiState.Loading && state !is DogUiState.Error)

            if (state is DogUiState.Success) {
                dogAdapter.submitList(state.photos)
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString().trim()
        requireContext().toast(item, Toast.LENGTH_LONG)

        lastSelectedBreed = item
        viewModel.getDogPhotosList(item)
    }

    private fun registerNetworkCallback() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val request =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                activity?.runOnUiThread {
                    if (viewModel.uiState.value is DogUiState.Error) {
                        viewModel.retryLastBreed(lastSelectedBreed)
                    }
                }
            }
        }

        connectivityManager.registerNetworkCallback(request, networkCallback!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        networkCallback?.let { callback ->
            connectivityManager?.unregisterNetworkCallback(callback)
        }
        _binding = null
    }

    private fun loadingBreedsList() {
        val list = resources.getStringArray(R.array.breeds_list)
        viewModel.setBreedsList(list)
    }
}