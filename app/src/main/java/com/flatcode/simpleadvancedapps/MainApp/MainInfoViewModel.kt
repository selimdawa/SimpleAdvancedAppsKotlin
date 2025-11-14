package com.flatcode.simpleadvancedapps.MainApp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.Unit.DATA

class MainInfoViewModel : ViewModel() {

    var dataMainInfo = MutableLiveData<ArrayList<MainInfo>>()

    private val S: BooleanArray = booleanArrayOf(
        false, false, false, true, false, false, true,
        false, false, true, true, true, false, false, true
    )

    private val S2: BooleanArray = booleanArrayOf(
        false, true, false, true, false, true, true,
        true, true, true, true, true, false, false, true
    )

    private val S3: BooleanArray = booleanArrayOf(
        false, false, false, false, false, true, false,
        false, true, true, false, false, false, false, true
    )

    private val S4: BooleanArray = booleanArrayOf(
        true, true, false, true, false, true, false,
        true, true, false, false, false, false, true, true
    )

    private val S5: BooleanArray = booleanArrayOf(
        false, true, false, false, false, false, false,
        false, false, false, false, false, false, false, false
    )

    private val S6: BooleanArray = booleanArrayOf(
        false, false, false, false, false, false, true,
        false, false, true, false, true, false, false, false
    )

    fun getInfoItems() {
        dataMainInfo.value = dataInfo
    }

    private val dataInfo: ArrayList<MainInfo>
        get() {
            val arrayList = ArrayList<MainInfo>()
            arrayList.add(MainInfo(DATA.DOGS, S[0], S2[0], S3[0], S4[0], S5[0], S6[0]))
            arrayList.add(MainInfo(DATA.COUNTRIES, S[1], S2[1], S3[1], S4[1], S5[1], S6[1]))
            arrayList.add(MainInfo(DATA.Calculator, S[2], S2[2], S3[2], S4[2], S5[2], S6[2]))
            arrayList.add(MainInfo(DATA.CRYPTO, S[3], S2[3], S3[3], S4[3], S5[3], S6[3]))
            arrayList.add(MainInfo(DATA.DICTIONARY, S[4], S2[4], S3[4], S4[4], S5[4], S6[4]))
            arrayList.add(MainInfo(DATA.MEALS, S[5], S2[5], S3[5], S4[5], S5[5], S6[5]))
            arrayList.add(MainInfo(DATA.VALORANT, S[6], S2[6], S3[6], S4[6], S5[6], S6[6]))
            arrayList.add(MainInfo(DATA.POP, S[7], S2[7], S3[7], S4[7], S5[7], S6[7]))
            arrayList.add(MainInfo(DATA.MOVIE, S[8], S2[8], S3[8], S4[8], S5[8], S6[8]))
            arrayList.add(MainInfo(DATA.STOCK_MARKET, S[9], S2[9], S3[9], S4[9], S5[9], S6[9]))
            arrayList.add(MainInfo(DATA.NEWS, S[10], S2[10], S3[10], S4[10], S5[10], S6[10]))
            arrayList.add(
                MainInfo(DATA.RICK_AND_MORTY, S[11], S2[11], S3[11], S4[11], S5[11], S6[11])
            )
            arrayList.add(MainInfo(DATA.WEATHER, S[12], S2[12], S3[12], S4[12], S5[12], S6[12]))
            arrayList.add(MainInfo(DATA.POKE, S[13], S2[13], S3[13], S4[13], S5[13], S6[13]))
            arrayList.add(
                MainInfo(DATA.TODO_NOTE, S[14], S2[14], S3[14], S4[14], S5[14], S6[14])
            )
            return arrayList
        }
}