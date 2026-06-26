package com.flatcode.simpleadvancedapps.movies.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moviesItemModel: MovieItemModel)

    @Delete
    suspend fun delete(moviesItemModel: MovieItemModel)

    @Query("SELECT * from movie_table")
    fun getAllMovies(): LiveData<List<MovieItemModel>>
}