package com.flatcode.simpleadvancedapps.dogs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class DogEntity(
    @PrimaryKey val imageUrl: String, val breed: String
)