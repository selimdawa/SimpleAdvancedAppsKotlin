package com.flatcode.simpleadvancedapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.utils.Constants

class MainInfoViewModel : ViewModel() {

    private val _dataMainInfo = MutableLiveData<List<MainInfo>>()
    val dataMainInfo: LiveData<List<MainInfo>> get() = _dataMainInfo

    fun getInfoItems() {
        _dataMainInfo.value = dataInfo
    }

    private val dataInfo: List<MainInfo>
        get() = listOf(
            MainInfo(Constants.DOGS, 1, 1, 0, 1),
            MainInfo(Constants.COUNTRIES, 1, 1, 1, 1),
            MainInfo(Constants.CALCULATOR, 1, 1, 1, 1),
            MainInfo(Constants.CRYPTO, 1, 1, 0, 1),
            MainInfo(Constants.DICTIONARY, 1, 1, 1, 1),
            MainInfo(Constants.MEALS, 1, 1, 1, 1),
            MainInfo(Constants.POP, 0, 1, 0, 1),
            MainInfo(Constants.MOVIE, 1, 1, 1, 1),
            MainInfo(Constants.NEWS, 1, 1, 1, 1),
            MainInfo(Constants.RICK_AND_MORTY, 1, 1, 0, 0),
            MainInfo(Constants.WEATHER, 0, 0, 0, 0),
            MainInfo(Constants.POKE, 0, 0, 0, 1),
            MainInfo(Constants.TODO_NOTE, 1, 1, 1, 1)
        )
}