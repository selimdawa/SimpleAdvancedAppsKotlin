package com.flatcode.simpleadvancedapps.pokemon.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pokemon_table")
@Parcelize
data class PokeEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img") val img: String
) : Parcelable

fun PokeItem.toDatabase() = PokeEntity(id = id, name = name, img = img)

fun PokeEntity.toDomain() = PokeItem(id = id, name = name, img = img)