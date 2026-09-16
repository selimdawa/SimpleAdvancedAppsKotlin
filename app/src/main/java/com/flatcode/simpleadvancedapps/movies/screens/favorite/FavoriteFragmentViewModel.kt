package com.flatcode.simpleadvancedapps.movies.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepository
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val allMovies: StateFlow<List<MovieItemModel>> = repository.allMovies
        .onEach { Timber.d("State updated: allMovies = $it") }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}