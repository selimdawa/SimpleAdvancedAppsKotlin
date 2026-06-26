package com.flatcode.simpleadvancedapps.MainApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.Unit.DATA

class MainInfoViewModel : ViewModel() {

    private val _dataMainInfo = MutableLiveData<List<MainInfo>>()
    val dataMainInfo: LiveData<List<MainInfo>> get() = _dataMainInfo

    private val s = booleanArrayOf(
        false, false, false, true, false, false, false,
        false, true, true, false, false, true
    )

    private val s2 = booleanArrayOf(
        false, true, false, true, false, true, true,
        true, true, true, false, false, true
    )

    private val s3 = booleanArrayOf(
        false, false, false, false, false, true, false,
        true, false, false, false, false, true
    )

    private val s4 = booleanArrayOf(
        true, true, false, true, false, true, true,
        true, false, false, false, true, true
    )

    private val s5 = booleanArrayOf(
        false, true, false, false, false, false, false,
        false, false, false, false, false, false
    )

    fun getInfoItems() {
        _dataMainInfo.value = dataInfo
    }

    private val dataInfo: List<MainInfo>
        get() = listOf(
            MainInfo(DATA.DOGS, s[0], s2[0], s3[0], s4[0], s5[0]),
            MainInfo(DATA.COUNTRIES, s[1], s2[1], s3[1], s4[1], s5[1]),
            MainInfo(DATA.Calculator, s[2], s2[2], s3[2], s4[2], s5[2]),
            MainInfo(DATA.CRYPTO, s[3], s2[3], s3[3], s4[3], s5[3]),
            MainInfo(DATA.DICTIONARY, s[4], s2[4], s3[4], s4[4], s5[4]),
            MainInfo(DATA.MEALS, s[5], s2[5], s3[5], s4[5], s5[5]),
            MainInfo(DATA.POP, s[6], s2[6], s3[6], s4[6], s5[6]),
            MainInfo(DATA.MOVIE, s[7], s2[7], s3[7], s4[7], s5[7]),
            MainInfo(DATA.NEWS, s[8], s2[8], s3[8], s4[8], s5[8]),
            MainInfo(DATA.RICK_AND_MORTY, s[9], s2[9], s3[9], s4[9], s5[9]),
            MainInfo(DATA.WEATHER, s[10], s2[10], s3[10], s4[10], s5[10]),
            MainInfo(DATA.POKE, s[11], s2[11], s3[11], s4[11], s5[11]),
            MainInfo(DATA.TODO_NOTE, s[12], s2[12], s3[12], s4[12], s5[12])
        )
}