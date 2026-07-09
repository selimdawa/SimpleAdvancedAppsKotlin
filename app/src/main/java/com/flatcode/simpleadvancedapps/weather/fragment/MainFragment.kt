package com.flatcode.simpleadvancedapps.weather.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.databinding.FragmentMainWeatherBinding
import com.flatcode.simpleadvancedapps.weather.DialogManager
import com.flatcode.simpleadvancedapps.weather.adatper.StateAdapter
import com.flatcode.simpleadvancedapps.weather.isPermissionGranted
import com.flatcode.simpleadvancedapps.weather.model.MainViewModel
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var fLocationClient: FusedLocationProviderClient
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val model: MainViewModel by activityViewModels()

    private val fList = listOf(HoursFragment.newInstance(), DaysFragment.newInstance())
    private val tList = listOf("Hours", "Days")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentCard()
        checkLocation()
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    private fun updateCurrentCard() {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMin = "${it.maxTemp}°C / ${it.minTemp}°C"
            binding.tvCity.text = it.city
            binding.tvData.text = it.time
            binding.tvCondition.text = it.condition
            binding.tvCurrentTemp.text = it.currentTemp.ifEmpty { maxMin }
            binding.tvMaxMin.text = if (it.currentTemp.isEmpty()) DATA.EMPTY else maxMin
            Picasso.get().load("https:" + it.imageUrl).into(binding.imgIcon)
        }
    }

    private fun getWeatherRequest(city: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = DATA.BASE_URL_WEATHER + DATA.API_KEY_WEATHER + "&q=" + city + "&days=" + "3" + "&aqi=no&alerts=no"

        val request = StringRequest(Request.Method.GET, url, { result ->
            parseWeatherData(result)
        }, { error ->
            Timber.d(error)
        })
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        parseCurrentDate(mainObject, list[0])
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")

        for (i in 0 until daysArray.length()) {
            val day = daysArray.getJSONObject(i)
            val dayDetail = day.getJSONObject("day")
            val condition = dayDetail.getJSONObject("condition")
            val item = WeatherModel(
                name, day.getString("date"),
                condition.getString("text"),
                DATA.EMPTY,
                dayDetail.getString("maxtemp_c").toFloat().toInt().toString(),
                dayDetail.getString("mintemp_c").toFloat().toInt().toString(),
                condition.getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        model.liveDataList.value = list
        return list
    }

    private fun parseCurrentDate(mainObject: JSONObject, weatherItem: WeatherModel) {
        val location = mainObject.getJSONObject("location")
        val current = mainObject.getJSONObject("current")
        val condition = current.getJSONObject("condition")
        val item = WeatherModel(
            location.getString("name"),
            current.getString("last_updated"),
            condition.getString("text"),
            current.getString("temp_c") + "°C",
            weatherItem.maxTemp,
            weatherItem.minTemp,
            condition.getString("icon"),
            weatherItem.hours
        )
        model.liveDataCurrent.value = item
    }

    private fun init() {
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val adapter = StateAdapter(activity as FragmentActivity, fList)
        binding.vp.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vp) { tab, pos ->
            tab.text = tList[pos]
        }.attach()

        binding.ibSync.setOnClickListener {
            checkLocation()
        }

        binding.ibSearch.setOnClickListener {
            DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    name?.let { it1 -> getWeatherRequest(it1) }
                }
            })
        }
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean {
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation() {
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener { task ->
                task.result?.let { location ->
                    getWeatherRequest("${location.latitude},${location.longitude}")
                }
            }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(requireContext(), "Permission is: $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}