package com.flatcode.simpleadvancedapps.weather.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "weather")
@Parcelize
data class WeatherModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String
) : Parcelable