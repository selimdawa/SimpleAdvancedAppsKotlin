package com.flatcode.simpleadvancedapps.movies.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.movies.data.room.dao.MoviesDao
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

@Database(entities = [MovieItemModel::class], version = 5)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MoviesDao

    companion object {
        @Volatile
        private var database: MoviesRoomDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): MoviesRoomDatabase = database ?: synchronized(lock) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                MoviesRoomDatabase::class.java,
                "db"
            ).build().also { database = it }
        }
    }
}