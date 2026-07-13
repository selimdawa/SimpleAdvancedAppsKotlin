package com.flatcode.simpleadvancedapps.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.movies.data.room.dao.MoviesDao
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

@Database(entities = [MovieItemModel::class], version = 5)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MoviesDao

}