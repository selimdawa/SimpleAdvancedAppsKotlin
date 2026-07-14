package com.flatcode.simpleadvancedapps.dictionary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
@Suppress("unused")
data class WordEntity(
    @PrimaryKey val word: String,
    val definition: String,
    val timestamp: Long = System.currentTimeMillis()
)
