package com.flatcode.simpleadvancedapps.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.weather.db.WeatherDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dao: WeatherDao) : ViewModel() {

    val liveDataList: StateFlow<List<WeatherModel>>
        field = MutableStateFlow<List<WeatherModel>>(emptyList())

    val liveDataCurrent: StateFlow<WeatherModel?>
        field = MutableStateFlow<WeatherModel?>(null)

    var lastCity: String? = null

    val savedWeather: StateFlow<WeatherModel?> =
        dao.getLatestWeather().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateCurrent(weather: WeatherModel) {
        liveDataCurrent.value = weather
    }

    fun updateList(list: List<WeatherModel>) {
        liveDataList.value = list
    }

    fun saveWeather(weather: WeatherModel) = viewModelScope.launch {
        dao.insertWeather(weather)
    }
}