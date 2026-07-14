package com.flatcode.simpleadvancedapps.pokemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.PokeDetailEntity
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.PokeEntity

@Database(entities = [PokeEntity::class, PokeDetailEntity::class], version = 1)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun getPokeDao(): PokeDao
}