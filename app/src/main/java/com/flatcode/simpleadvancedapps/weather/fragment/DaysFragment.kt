package com.flatcode.simpleadvancedapps.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.weather.adatper.WeatherAdapter
import com.flatcode.simpleadvancedapps.databinding.FragmentDaysBinding
import com.flatcode.simpleadvancedapps.weather.model.MainViewModel
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DaysFragment : Fragment(), WeatherAdapter.Listener {

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph_weather)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeData()
    }

    private fun initRcView() {
        adapter = WeatherAdapter(this)
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DaysFragment.adapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.liveDataList.collect { weatherList ->
                    adapter.submitList(weatherList)
                }
            }
        }
    }

    override fun onClick(item: WeatherModel) {
        model.updateCurrent(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = DaysFragment()
    }
}