package com.flatcode.simpleadvancedapps.weather.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData <WeatherModel>()
    val liveDataList = MutableLiveData <List<WeatherModel>>()
}