package com.flatcode.simpleadvancedapps.weather.db

import androidx.room.*
import com.flatcode.simpleadvancedapps.weather.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY id DESC LIMIT 1")
    fun getLatestWeather(): Flow<WeatherModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherModel)
}