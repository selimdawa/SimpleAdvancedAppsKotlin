package com.flatcode.simpleadvancedapps.weather.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentDaysBinding
import com.flatcode.simpleadvancedapps.news.common.viewBinding
import com.flatcode.simpleadvancedapps.weather.adatper.WeatherAdapter
import com.flatcode.simpleadvancedapps.weather.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DaysFragment : Fragment(R.layout.fragment_days) {

    private val binding by viewBinding(FragmentDaysBinding::bind)
    private val model: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph_weather)
    private val adapter = WeatherAdapter { item ->
        model.updateCurrent(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeData()
    }

    private fun initRcView() {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DaysFragment.adapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            model.liveDataList
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { weatherList ->
                    adapter.submitList(weatherList)
                }
        }
    }

    companion object {
        fun newInstance() = DaysFragment()
    }
}