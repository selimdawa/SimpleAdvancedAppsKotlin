package com.flatcode.simpleadvancedapps.dogs.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs WHERE breed = :breed")
    fun getDogsByBreed(breed: String): Flow<List<DogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>)

    @Query("DELETE FROM dogs WHERE breed = :breed")
    suspend fun deleteDogsByBreed(breed: String)
}