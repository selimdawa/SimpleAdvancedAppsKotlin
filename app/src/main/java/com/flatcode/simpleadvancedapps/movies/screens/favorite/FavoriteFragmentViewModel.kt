package com.flatcode.simpleadvancedapps.movies.screens.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepository
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun getAllMovies(): LiveData<List<MovieItemModel>> {
        return repository.allMovies
    }
}