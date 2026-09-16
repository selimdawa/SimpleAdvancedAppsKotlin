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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dao: WeatherDao) : ViewModel() {

    private val _weatherStateList = MutableStateFlow<List<WeatherModel>>(emptyList())
    val weatherStateList: StateFlow<List<WeatherModel>> = _weatherStateList

    private val _weatherStateCurrent = MutableStateFlow<WeatherModel?>(null)
    val weatherStateCurrent: StateFlow<WeatherModel?> = _weatherStateCurrent

    var lastCity: String? = null

    val savedWeather: StateFlow<WeatherModel?> =
        dao.getLatestWeather().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateCurrent(weather: WeatherModel) {
        _weatherStateCurrent.value = weather
        Timber.d("State updated: weatherStateCurrent = $weather")
    }

    fun updateList(list: List<WeatherModel>) {
        _weatherStateList.value = list
        Timber.d("State updated: weatherStateList = $list")
    }

    fun saveWeather(weather: WeatherModel) = viewModelScope.launch {
        dao.insertWeather(weather)
    }
}