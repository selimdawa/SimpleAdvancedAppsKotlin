package com.flatcode.simpleadvancedapps.calculator.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: CalculatorEntity)

    @Query("SELECT * FROM calculator_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<CalculatorEntity>>

    @Query("DELETE FROM calculator_history")
    suspend fun clearHistory()
}