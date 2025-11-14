package com.flatcode.simpleadvancedapps.countries.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.countries.Adapter.CountryAdapter
import com.flatcode.simpleadvancedapps.countries.ViewModel.DashboardViewModel
import com.flatcode.simpleadvancedapps.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var binding: FragmentDashboardBinding? = null
    private lateinit var viewModel: DashboardViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(LayoutInflater.from(context), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        viewModel.refreshData()

        binding!!.toolbar.nameSpace.text = DATA.COUNTRIES

        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.adapter = countryAdapter

        binding!!.swipe.setOnRefreshListener {
            binding!!.recyclerView.visibility = View.GONE
            binding!!.errorText.visibility = View.GONE
            binding!!.refreshBar.visibility = View.VISIBLE
            viewModel.refreshData()
            binding!!.swipe.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countries?.let {
                binding!!.recyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        }

        viewModel.countryError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it) {
                    binding!!.errorText.visibility = View.VISIBLE
                } else {
                    binding!!.errorText.visibility = View.GONE
                }
            }
        }

        viewModel.countryLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    binding!!.refreshBar.visibility = View.VISIBLE
                    binding!!.errorText.visibility = View.GONE
                    binding!!.recyclerView.visibility = View.GONE
                } else {
                    binding!!.refreshBar.visibility = View.GONE
                }
            }
        }
    }
}