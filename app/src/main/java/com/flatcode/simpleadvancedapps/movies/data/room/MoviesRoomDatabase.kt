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
        private var database: MoviesRoomDatabase? = null

        fun getInstance(context: Context): MoviesRoomDatabase {
            return if (database == null) {
                database =
                    Room.databaseBuilder(context, MoviesRoomDatabase::class.java, "db").build()
                database as MoviesRoomDatabase
            } else {
                database as MoviesRoomDatabase
            }
        }
    }
}