package com.flatcode.simpleadvancedapps.crypto.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_details")
data class CoinDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val symbol: String,
    val description: String,
    val logo: String
)