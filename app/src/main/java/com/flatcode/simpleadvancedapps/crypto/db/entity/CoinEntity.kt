package com.flatcode.simpleadvancedapps.crypto.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "coins")
@Parcelize
data class CoinEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val percentChange24h: Double,
    val lastUpdated: String
) : Parcelable