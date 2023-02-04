package com.flatcode.simpleadvancedapps.MainApp

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.CLASS
import com.flatcode.simpleadvancedapps.Unit.DATA

class MainViewModel : ViewModel() {
    var dataMain = MutableLiveData<ArrayList<Main>>()

    private val I: IntArray = intArrayOf(1, 1, 1, 1, 2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1)

    fun getItems(recyclerView: RecyclerView, bar: ProgressBar) {
        dataMain.value = data
        bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private val data: ArrayList<Main>
        get() {
            val arrayList = ArrayList<Main>()
            arrayList.add(Main(R.drawable.ic_home_work, DATA.DOGS, I[0], CLASS.I1))
            arrayList.add(Main(R.drawable.ic_flag, DATA.COUNTRIES, I[1], CLASS.I2))
            arrayList.add(Main(R.drawable.ic_calculate, DATA.Calculator, I[2], CLASS.I3))
            arrayList.add(Main(R.drawable.ic_monetization, DATA.CRYPTO, I[3], CLASS.I4))
            arrayList.add(Main(R.drawable.ic_words, DATA.DICTIONARY, I[4], CLASS.I5))
            arrayList.add(Main(R.drawable.ic_meal, DATA.MEALS, I[5], CLASS.I6))
            arrayList.add(Main(R.drawable.logo_valorant, DATA.VALORANT, I[6], CLASS.I7))
            arrayList.add(Main(R.drawable.ic_game, DATA.POP, I[7], CLASS.I8))
            arrayList.add(Main(R.drawable.ic_movie, DATA.MOVIE, I[8], CLASS.I9))
            arrayList.add(Main(R.drawable.ic_company, DATA.STOCK_MARKET, I[9], CLASS.I10))
            arrayList.add(Main(R.drawable.ic_feed, DATA.NEWS, I[10], CLASS.I11))
            arrayList.add(Main(R.drawable.ic_child, DATA.RICK_AND_MORTY, I[11], CLASS.I12))
            arrayList.add(Main(R.drawable.ic_nights, DATA.WEATHER, I[12], CLASS.I13))
            arrayList.add(Main(R.drawable.ic_gamepad, DATA.POKE, I[13], CLASS.I14))
            arrayList.add(Main(R.drawable.ic_note, DATA.TODO_NOTE, I[14], CLASS.I15))
            return arrayList
        }
}