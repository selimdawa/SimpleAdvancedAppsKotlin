package com.flatcode.simpleadvancedapps.dogs.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.databinding.FragmentDogBinding
import com.flatcode.simpleadvancedapps.dogs.viewmodel.DogApiStatus
import com.flatcode.simpleadvancedapps.dogs.viewmodel.DogViewModel

class DogFragment : Fragment(R.layout.fragment_dog), AdapterView.OnItemClickListener {

    private val binding by viewBinding(FragmentDogBinding::bind)
    private val viewModel: DogViewModel by viewModels()
    private val dogAdapter = DogAdapter()
    private var lastSelectedBreed: String? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingBreedsList()

        with(binding.recyclerViewDog) {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.toolbar.nameSpace.text = DATA.DOGS
        observer()
        registerNetworkCallback()
    }

    private fun observer() {
        viewModel.breedsList.observe(viewLifecycleOwner) { newList ->
            val adapter = ArrayAdapter(requireContext(), R.layout.list_breeds, newList)
            with(binding.autoCompleteTextView) {
                setAdapter(adapter)
                onItemClickListener = this@DogFragment
            }
        }

        viewModel.photosDog.observe(viewLifecycleOwner) { photos ->
            dogAdapter.submitList(photos)
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DogApiStatus.START -> {
                    binding.statusImageError.visibility = View.GONE
                    binding.recyclerViewDog.visibility = View.VISIBLE
                }
                DogApiStatus.LOADING -> {
                    binding.statusImageError.visibility = View.GONE
                    binding.recyclerViewDog.visibility = View.GONE
                }
                DogApiStatus.ERROR -> {
                    binding.statusImageError.visibility = View.VISIBLE
                    binding.recyclerViewDog.visibility = View.GONE
                }
                DogApiStatus.DONE -> {
                    binding.statusImageError.visibility = View.GONE
                    binding.recyclerViewDog.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), item, Toast.LENGTH_LONG).show()

        lastSelectedBreed = item
        viewModel.getDogPhotosList(requireContext(), item)
    }

    private fun registerNetworkCallback() {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                activity?.runOnUiThread {
                    val currentStatus = viewModel.status.value
                    val breedToFetch = lastSelectedBreed

                    if (currentStatus == DogApiStatus.ERROR && breedToFetch != null) {
                        viewModel.getDogPhotosList(requireContext(), breedToFetch)
                    }
                }
            }
        }

        connectivityManager.registerNetworkCallback(request, networkCallback!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        networkCallback?.let { callback ->
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }

    private fun loadingBreedsList() {
        val list = resources.getStringArray(R.array.breeds_list)
        viewModel.setBreedsList(list)
    }
}