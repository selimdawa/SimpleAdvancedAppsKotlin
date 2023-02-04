package com.flatcode.simpleadvancedapps.movies.data.room.repository

import androidx.lifecycle.LiveData
import com.flatcode.simpleadvancedapps.movies.data.room.dao.MoviesDao
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

class MoviesRepositoryRealization(private val moviesDao: MoviesDao): MoviesRepository {

    override val allMovies: LiveData<List<MovieItemModel>>
        get() = moviesDao.getAllMovies()

    override suspend fun insertMovie(movieItemModel: MovieItemModel, onSuccess: () -> Unit) {
        moviesDao.insert(movieItemModel)
        onSuccess()
    }

    override suspend fun deleteMovie(movieItemModel: MovieItemModel, onSuccess: () -> Unit) {
        moviesDao.delete(movieItemModel)
        onSuccess()
    }

}