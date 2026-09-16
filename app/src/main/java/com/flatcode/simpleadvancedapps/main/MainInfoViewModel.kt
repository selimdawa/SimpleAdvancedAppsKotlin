package com.flatcode.simpleadvancedapps.main

import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.utils.DATA
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class MainInfoViewModel : ViewModel() {

    val dataMainInfo: StateFlow<List<MainInfo>>
        field = MutableStateFlow(emptyList())

    fun getInfoItems() {
        (dataMainInfo as MutableStateFlow).value = dataInfo
        Timber.d("State updated: dataMainInfo = $dataInfo")
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