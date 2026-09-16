package com.flatcode.simpleadvancedapps.countries.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.countries.adapter.CountryAdapter
import com.flatcode.simpleadvancedapps.countries.viewModel.DashboardViewModel
import com.flatcode.simpleadvancedapps.databinding.FragmentDashboardBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.nav_graph_country)
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshData()

        with(binding) {
            toolbar.nameSpace.text = DATA.COUNTRIES
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = countryAdapter

            swipe.setOnRefreshListener {
                recyclerView.visibility = View.GONE
                errorText.visibility = View.GONE
                refreshBar.visibility = View.VISIBLE
                viewModel.refreshData()
                swipe.isRefreshing = false
            }
        }
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.countries.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { countries ->
                    binding.recyclerView.visibility = View.VISIBLE
                    countryAdapter.updateCountryList(countries)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.countryError.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { error ->
                    binding.errorText.visibility = if (error == true) View.VISIBLE else View.GONE
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.countryLoading.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { loading ->
                    if (loading == true) {
                        with(binding) {
                            refreshBar.visibility = View.VISIBLE
                            errorText.visibility = View.GONE
                            recyclerView.visibility = View.GONE
                        }
                    } else {
                        binding.refreshBar.visibility = View.GONE
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}