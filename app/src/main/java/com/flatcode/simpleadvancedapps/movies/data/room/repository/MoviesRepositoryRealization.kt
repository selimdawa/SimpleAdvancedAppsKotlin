package com.flatcode.simpleadvancedapps.movies.data.room.repository

import androidx.lifecycle.LiveData
import com.flatcode.simpleadvancedapps.movies.data.room.dao.MoviesDao
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel
import javax.inject.Inject

class MoviesRepositoryRealization @Inject constructor(private val moviesDao: MoviesDao):
    MoviesRepository {

    override val allMovies: LiveData<List<MovieItemModel>>
        get() = moviesDao.getAllMovies()

    override suspend fun insertMovie(movieItemModel: MovieItemModel) {
        moviesDao.insert(movieItemModel)
    }

    override suspend fun deleteMovie(movieItemModel: MovieItemModel) {
        moviesDao.delete(movieItemModel)
    }
}