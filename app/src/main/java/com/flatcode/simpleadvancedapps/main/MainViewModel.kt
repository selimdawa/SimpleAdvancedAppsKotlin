package com.flatcode.simpleadvancedapps.main

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.calculator.view.CalculatorActivity
import com.flatcode.simpleadvancedapps.countries.CountriesActivity
import com.flatcode.simpleadvancedapps.crypto.ui.CryptoActivity
import com.flatcode.simpleadvancedapps.dictionary.DictionaryActivity
import com.flatcode.simpleadvancedapps.dogs.view.DogsActivity
import com.flatcode.simpleadvancedapps.meals.activities.MealsActivity
import com.flatcode.simpleadvancedapps.movies.MoviesActivity
import com.flatcode.simpleadvancedapps.news.ui.activity.NewsActivity
import com.flatcode.simpleadvancedapps.pokemon.ui.view.PokemonActivity
import com.flatcode.simpleadvancedapps.pop.PopActivity
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.RickAndMortyActivity
import com.flatcode.simpleadvancedapps.todoNote.TodoNoteActivity
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.weather.WeatherActivity

class MainViewModel : ViewModel() {

    val dataMain: LiveData<List<Main>>
        field = MutableLiveData<List<Main>>()

    private val i = intArrayOf(1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1)

    fun getItems(recyclerView: RecyclerView, bar: ProgressBar) {
        dataMain.value = data
        bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private val data: List<Main>
        get() = listOf(
            Main(R.drawable.ic_home_work, DATA.DOGS, i[0], DogsActivity::class.java),
            Main(R.drawable.ic_flag, DATA.COUNTRIES, i[1], CountriesActivity::class.java),
            Main(R.drawable.ic_calculate, DATA.CALCULATOR, i[2], CalculatorActivity::class.java),
            Main(R.drawable.ic_monetization, DATA.CRYPTO, i[3], CryptoActivity::class.java),
            Main(R.drawable.ic_words, DATA.DICTIONARY, i[4], DictionaryActivity::class.java),
            Main(R.drawable.ic_meal, DATA.MEALS, i[5], MealsActivity::class.java),
            Main(R.drawable.ic_game, DATA.POP, i[6], PopActivity::class.java),
            Main(R.drawable.ic_movie, DATA.MOVIE, i[7], MoviesActivity::class.java),
            Main(R.drawable.ic_feed, DATA.NEWS, i[8], NewsActivity::class.java),
            Main(R.drawable.ic_child, DATA.RICK_AND_MORTY, i[9], RickAndMortyActivity::class.java),
            Main(R.drawable.ic_nights, DATA.WEATHER, i[10], WeatherActivity::class.java),
            Main(R.drawable.ic_gamepad, DATA.POKE, i[11], PokemonActivity::class.java),
            Main(R.drawable.ic_note, DATA.TODO_NOTE, i[12], TodoNoteActivity::class.java),
        )
}