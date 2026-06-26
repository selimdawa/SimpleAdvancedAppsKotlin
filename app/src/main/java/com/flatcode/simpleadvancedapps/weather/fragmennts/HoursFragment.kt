package com.flatcode.simpleadvancedapps.weather.fragmennts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.databinding.FragmentHoursBinding
import com.flatcode.simpleadvancedapps.weather.adatpers.WeatherAdapter
import com.flatcode.simpleadvancedapps.weather.models.MainViewModel
import com.flatcode.simpleadvancedapps.weather.models.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private var _binding: FragmentHoursBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getHoursList(it))
        }
    }

    fun initRcView() {
        adapter = WeatherAdapter(null)
        binding.rcViewHours.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewHours.adapter = adapter
    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        val hoursArray = JSONArray(wItem.hours)
        for (i in 0 until hoursArray.length()) {
            val hourObject = hoursArray.getJSONObject(i)
            val conditionObject = hourObject.getJSONObject("condition")
            val item = WeatherModel(
                wItem.city,
                hourObject.getString("time"),
                conditionObject.getString("text"),
                hourObject.getString("temp_c").toFloat().toInt().toString() + "°C",
                DATA.EMPTY,
                DATA.EMPTY,
                conditionObject.getString("icon"),
                DATA.EMPTY
            )
            list.add(item)
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}