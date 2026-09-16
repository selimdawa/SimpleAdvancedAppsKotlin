package com.flatcode.simpleadvancedapps.movies.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.movies.data.retrofit.RetrofitRepository
import com.flatcode.simpleadvancedapps.movies.models.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel() {

    val uiState: StateFlow<MoviesUiState>
        field = MutableStateFlow(MoviesUiState.Loading)

    init {
        getMoviesRetrofit()
    }

    private fun getMoviesRetrofit() {
        viewModelScope.launch {
            (uiState as MutableStateFlow).value = MoviesUiState.Loading
            Timber.d("State updated: uiState = MoviesUiState.Loading")
            try {
                val response = repository.getMovie()
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    val successState = MoviesUiState.Success(movies)
                    (uiState as MutableStateFlow).value = successState
                    Timber.d("State updated: uiState = $successState")
                } else {
                    val errorState = MoviesUiState.Error("Error: ${response.code()}")
                    (uiState as MutableStateFlow).value = errorState
                    Timber.d("State updated: uiState = $errorState")
                }
            } catch (e: Exception) {
                val errorState = MoviesUiState.Error(e.localizedMessage ?: "Unknown error")
                (uiState as MutableStateFlow).value = errorState
                Timber.d("State updated: uiState = $errorState")
            }
        }
    }
}