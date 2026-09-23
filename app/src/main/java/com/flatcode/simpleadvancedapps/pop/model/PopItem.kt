package com.flatcode.simpleadvancedapps.pop.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "funko_pops")
@Parcelize
data class PopItem(
    @PrimaryKey val id: Int,
    val name: String,
    val img: String,
    val series: String,
) : Parcelable