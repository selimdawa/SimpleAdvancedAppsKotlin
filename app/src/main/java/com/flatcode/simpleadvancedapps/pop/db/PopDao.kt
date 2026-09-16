package com.flatcode.simpleadvancedapps.pop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.pop.model.PopItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PopDao {
    @Query("SELECT * FROM funko_pops")
    fun getAllPops(): Flow<List<PopItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPops(pops: List<PopItem>)

    @Query("DELETE FROM funko_pops")
    fun deleteAllPops()
}