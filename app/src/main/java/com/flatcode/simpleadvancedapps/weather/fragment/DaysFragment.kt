package com.flatcode.simpleadvancedapps.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.databinding.FragmentDaysBinding
import com.flatcode.simpleadvancedapps.weather.adatper.WeatherAdapter
import com.flatcode.simpleadvancedapps.weather.model.MainViewModel
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel

class DaysFragment : Fragment(), WeatherAdapter.Listener {

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun init() {
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        adapter = WeatherAdapter(this@DaysFragment)
        binding.rcView.adapter = adapter
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}