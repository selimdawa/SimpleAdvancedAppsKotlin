package com.flatcode.simpleadvancedapps.movies.data.room.repository

import androidx.lifecycle.LiveData
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

interface MoviesRepository {
    val allMovies: LiveData<List<MovieItemModel>>
    suspend fun insertMovie(movieItemModel: MovieItemModel)
    suspend fun deleteMovie(movieItemModel: MovieItemModel)
}