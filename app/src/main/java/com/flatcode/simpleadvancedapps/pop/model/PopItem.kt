package com.flatcode.simpleadvancedapps.pop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "funko_pops")
data class PopItem(
    @PrimaryKey val id: Int,
    val name: String,
    val img: String,
    val series: String,
)