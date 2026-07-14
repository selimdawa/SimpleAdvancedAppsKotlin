package com.flatcode.simpleadvancedapps.pop.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentHomePopBinding
import com.flatcode.simpleadvancedapps.pop.adapter.FunkoListAdapter
import com.flatcode.simpleadvancedapps.pop.adapter.PopListener
import com.flatcode.simpleadvancedapps.pop.viewmodel.FunkoViewModel
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomePopBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FunkoViewModel by hiltNavGraphViewModels(R.id.nav_graph_pop)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomePopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FunkoListAdapter(PopListener { pop ->
            viewModel.onPopClicked(pop)
        })

        binding.apply {
            toolbar.nameSpace.text = DATA.POP
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE
            searchEtLayout.visibility = View.INVISIBLE

            searchEt.doAfterTextChanged { text ->
                viewModel.filterText.value = text.toString()
                viewModel.filter()
            }
        }

        handleBackPress()
        loadData(adapter)
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.isListFiltered.value == true) {
                        binding.searchEt.text?.clear()
                        viewModel.filterText.value = ""
                        viewModel.filter()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }

    private fun loadData(adapter: FunkoListAdapter) {
        showComponents()

        viewModel.fetchData()

        viewModel.pops.observe(viewLifecycleOwner) { pops ->
            if (pops != null && pops.isNotEmpty()) {
                binding.apply {
                    progressBar.visibility = View.GONE
                    searchEtLayout.visibility = View.VISIBLE
                }
            }

            if (viewModel.isListFiltered.value == true) {
                adapter.submitList(viewModel.getFilteredList(viewModel.filterText.value.toString()))
            } else {
                adapter.submitList(pops)
            }
        }

        viewModel.isListFiltered.observe(viewLifecycleOwner) { isFiltered ->
            if (isFiltered) {
                adapter.submitList(viewModel.getFilteredList(viewModel.filterText.value.toString()))
            } else {
                adapter.submitList(viewModel.pops.value)
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.fetchData()
            binding.swipeLayout.isRefreshing = false
        }

        if (!isOnline(requireContext())) {
            // Optional: show a message that data might be old
            Timber.i("Device is offline, showing cached data")
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

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}