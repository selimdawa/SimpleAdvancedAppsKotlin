package com.flatcode.simpleadvancedapps.dictionary.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "words")
@Parcelize
data class WordEntity(
    @PrimaryKey val word: String,
    val definition: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable