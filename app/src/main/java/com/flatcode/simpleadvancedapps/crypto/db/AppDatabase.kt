package com.flatcode.simpleadvancedapps.crypto.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.crypto.db.dao.CoinDao
import com.flatcode.simpleadvancedapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinEntity

@Database(
    entities = [CoinEntity::class, CoinDetailEntity::class], version = 1, exportSchema = false
)
@Suppress("unused")
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun coinDetailDao(): CoinDetailDao
}