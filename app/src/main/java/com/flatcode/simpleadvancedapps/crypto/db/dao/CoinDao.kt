package com.flatcode.simpleadvancedapps.crypto.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
@Suppress("unused")
interface CoinDao {
    @Query("SELECT * FROM coins")
    fun getAllCoins(): Flow<List<CoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Query("DELETE FROM coins")
    suspend fun deleteAllCoins()
}