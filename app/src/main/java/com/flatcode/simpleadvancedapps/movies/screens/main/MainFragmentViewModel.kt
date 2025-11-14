package com.flatcode.simpleadvancedapps.movies.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.Unit.DATA.REALIZATION
import com.flatcode.simpleadvancedapps.movies.data.retrofit.RetrofitRepository
import com.flatcode.simpleadvancedapps.movies.data.room.MoviesRoomDatabase
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepositoryRealization
import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RetrofitRepository()
    val myMovies: MutableLiveData<Response<MoviesModel>> = MutableLiveData()
    val context = application

    fun getMoviesRetrofit() {
        viewModelScope.launch {
            myMovies.value = repository.getMovie()
        }
    }

    fun initDatabase() {
        val daoMovie = MoviesRoomDatabase.getInstance(context).getMovieDao()
        REALIZATION = MoviesRepositoryRealization(daoMovie)
    }
}