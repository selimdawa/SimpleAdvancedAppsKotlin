package com.flatcode.simpleadvancedapps.movies.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.movies.data.retrofit.RetrofitRepository
import com.flatcode.simpleadvancedapps.movies.data.room.MoviesRoomDatabase
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepositoryRealization
import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import com.flatcode.simpleadvancedapps.utils.DATA.REALIZATION
import kotlinx.coroutines.launch
import retrofit2.Response

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RetrofitRepository()
    private val _myMovies = MutableLiveData<Response<MoviesModel>>()
    val myMovies: LiveData<Response<MoviesModel>> get() = _myMovies
    private val context = application

    fun getMoviesRetrofit() {
        viewModelScope.launch {
            _myMovies.value = repository.getMovie()
        }
    }

    fun initDatabase() {
        val daoMovie = MoviesRoomDatabase.getInstance(context).getMovieDao()
        REALIZATION = MoviesRepositoryRealization(daoMovie)
    }
}