package com.flatcode.simpleadvancedapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.utils.DATA

class MainInfoViewModel : ViewModel() {

    val dataMainInfo: LiveData<List<MainInfo>>
        field = MutableLiveData<List<MainInfo>>()

    fun getInfoItems() {
        dataMainInfo.value = dataInfo
    }

    private val dataInfo: List<MainInfo>
        get() = listOf(
            MainInfo(DATA.DOGS, 1, 1, 1, 1),
            MainInfo(DATA.COUNTRIES, 1, 1, 1, 1),
            MainInfo(DATA.CALCULATOR, 1, 1, 1, 1),
            MainInfo(DATA.CRYPTO, 1, 1, 1, 1),
            MainInfo(DATA.DICTIONARY, 1, 1, 1, 1),
            MainInfo(DATA.MEALS, 1, 1, 1, 1),
            MainInfo(DATA.POP, 1, 1, 1, 1),
            MainInfo(DATA.MOVIE, 1, 1, 1, 1),
            MainInfo(DATA.NEWS, 1, 1, 1, 1),
            MainInfo(DATA.RICK_AND_MORTY, 1, 1, 1, 1),
            MainInfo(DATA.WEATHER, 1, 1, 1, 1),
            MainInfo(DATA.POKE, 1, 1, 1, 1),
            MainInfo(DATA.TODO_NOTE, 1, 1, 1, 1)
        )
}