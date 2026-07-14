package com.flatcode.simpleadvancedapps.pop.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.flatcode.simpleadvancedapps.Application
import com.flatcode.simpleadvancedapps.utils.Constants
import com.flatcode.simpleadvancedapps.databinding.FragmentHomePopBinding
import com.flatcode.simpleadvancedapps.pop.adapters.FunkoListAdapter
import com.flatcode.simpleadvancedapps.pop.adapters.PopListener
import com.flatcode.simpleadvancedapps.pop.viewmodels.FunkoViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomePopBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FunkoViewModel by activityViewModels {
        FunkoViewModel.FunkoViewModelFactory(
            (activity?.application as Application).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomePopBinding.inflate(inflater, container, false)

        val adapter = FunkoListAdapter(PopListener { pop ->
            viewModel.onPopClicked(pop)
        })

        binding.apply {
            toolbar.nameSpace.text = Constants.POP
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE
            searchEtLayout.visibility = View.INVISIBLE

            searchEt.doAfterTextChanged { text ->
                viewModel.filterText.value = text.toString()
                viewModel.filter()
            }
        }

        loadData(adapter)

        return binding.root
    }

    fun loadData(adapter: FunkoListAdapter) {
        if (isOnline(requireContext())) {
            binding.swipeLayout.setOnRefreshListener { binding.swipeLayout.isRefreshing = false }
            showComponents()

            viewModel.fetchData().observe(viewLifecycleOwner) { _ ->
                binding.apply {
                    progressBar.visibility = View.GONE
                    searchEtLayout.visibility = View.VISIBLE
                }

                viewModel.isListFiltered.observe(viewLifecycleOwner) { isFiltered ->
                    if (isFiltered) {
                        adapter.submitList(viewModel.getFilteredList(viewModel.filterText.value.toString()))
                    } else {
                        adapter.submitList(viewModel.pops.value)
                    }
                }
            }
        } else {
            binding.apply {
                hideComponents()
                swipeLayout.setOnRefreshListener {
                    loadData(adapter)
                    swipeLayout.isRefreshing = false
                }
            }
        }
    }

    private fun showComponents() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
            searchEtLayout.visibility = View.VISIBLE
            netTv.visibility = View.GONE
        }
    }

    private fun hideComponents() {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            searchEtLayout.visibility = View.GONE
            netTv.visibility = View.VISIBLE
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}