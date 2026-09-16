package com.flatcode.simpleadvancedapps.movies.data.room.repository

import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val allMovies: Flow<List<MovieItemModel>>
    suspend fun insertMovie(movieItemModel: MovieItemModel)
    suspend fun deleteMovie(movieItemModel: MovieItemModel)
}