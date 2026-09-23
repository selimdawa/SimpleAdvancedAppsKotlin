package com.flatcode.simpleadvancedapps.dogs.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dogs")
@Parcelize
data class DogEntity(
    @PrimaryKey val imageUrl: String, val breed: String
) : Parcelable