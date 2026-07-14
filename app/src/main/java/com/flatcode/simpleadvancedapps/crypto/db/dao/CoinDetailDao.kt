package com.flatcode.simpleadvancedapps.crypto.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDetailDao {
    @Query("SELECT * FROM coin_details WHERE id = :id")
    fun getCoinDetail(id: Int): Flow<CoinDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetail(detail: CoinDetailEntity)
}
