package com.flatcode.simpleadvancedapps.crypto.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "coin_details")
@Parcelize
data class CoinDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val symbol: String,
    val description: String,
    val logo: String
) : Parcelable