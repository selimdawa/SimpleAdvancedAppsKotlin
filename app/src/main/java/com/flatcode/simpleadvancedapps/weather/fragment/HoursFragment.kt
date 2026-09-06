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
import com.flatcode.simpleadvancedapps.databinding.FragmentHoursBinding
import com.flatcode.simpleadvancedapps.news.common.viewBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.weather.adatper.WeatherAdapter
import com.flatcode.simpleadvancedapps.weather.model.MainViewModel
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray

@AndroidEntryPoint
class HoursFragment : Fragment(R.layout.fragment_hours) {

    private val binding by viewBinding(FragmentHoursBinding::bind)
    private val model: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph_weather)
    private val adapter = WeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeData()
    }

    private fun initRcView() {
        binding.rcViewHours.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HoursFragment.adapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            model.liveDataCurrent
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { weatherItem ->
                    weatherItem?.let { adapter.submitList(getHoursList(it)) }
                }
        }
    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
        if (wItem.hours.isEmpty() || (wItem.hours == DATA.EMPTY)) return emptyList()
        val hoursArray = JSONArray(wItem.hours)

        return (0 until hoursArray.length()).map { i ->
            val hourObject = hoursArray.getJSONObject(i)
            val conditionObject = hourObject.getJSONObject("condition")
            val tempInt = hourObject.getString("temp_c").toFloat().toInt()

            WeatherModel(
                city = wItem.city,
                time = hourObject.getString("time"),
                condition = conditionObject.getString("text"),
                currentTemp = "$tempInt°C",
                maxTemp = DATA.EMPTY,
                minTemp = DATA.EMPTY,
                imageUrl = conditionObject.getString("icon"),
                hours = DATA.EMPTY,
            )
        }
    }

    companion object {
        fun newInstance() = HoursFragment()
    }
}