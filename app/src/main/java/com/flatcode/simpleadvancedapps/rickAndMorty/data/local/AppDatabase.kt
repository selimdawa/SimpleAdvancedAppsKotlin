package com.flatcode.simpleadvancedapps.rickAndMorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.*

@Database(entities = [CharacterModel::class, EpisodeModel::class, LocationModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}