package com.flatcode.simpleadvancedapps.pop.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.pop.model.PopItem

@Dao
interface PopDao {
    @Query("SELECT * FROM funko_pops")
    fun getAllPops(): LiveData<List<PopItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPops(pops: List<PopItem>)

    @Query("DELETE FROM funko_pops")
    fun deleteAllPops()
}