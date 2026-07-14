package com.flatcode.simpleadvancedapps.main

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.CLASS
import com.flatcode.simpleadvancedapps.utils.Constants

class MainViewModel : ViewModel() {

    private val _dataMain = MutableLiveData<List<Main>>()
    val dataMain: LiveData<List<Main>> get() = _dataMain

    private val i = intArrayOf(1, 1, 1, 1, 2, 3, 1, 1, 1, 1, 1, 1, 1)

    fun getItems(recyclerView: RecyclerView, bar: ProgressBar) {
        _dataMain.value = data
        bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private val data: List<Main>
        get() = listOf(
            Main(R.drawable.ic_home_work, Constants.DOGS, i[0], CLASS.I1),
            Main(R.drawable.ic_flag, Constants.COUNTRIES, i[1], CLASS.I2),
            Main(R.drawable.ic_calculate, Constants.CALCULATOR, i[2], CLASS.I3),
            Main(R.drawable.ic_monetization, Constants.CRYPTO, i[3], CLASS.I4),
            Main(R.drawable.ic_words, Constants.DICTIONARY, i[4], CLASS.I5),
            Main(R.drawable.ic_meal, Constants.MEALS, i[5], CLASS.I6),
            Main(R.drawable.ic_game, Constants.POP, i[6], CLASS.I7),
            Main(R.drawable.ic_movie, Constants.MOVIE, i[7], CLASS.I8),
            Main(R.drawable.ic_feed, Constants.NEWS, i[8], CLASS.I9),
            Main(R.drawable.ic_child, Constants.RICK_AND_MORTY, i[9], CLASS.I10),
            Main(R.drawable.ic_nights, Constants.WEATHER, i[10], CLASS.I11),
            Main(R.drawable.ic_gamepad, Constants.POKE, i[11], CLASS.I12),
            Main(R.drawable.ic_note, Constants.TODO_NOTE, i[12], CLASS.I13)
        )
}