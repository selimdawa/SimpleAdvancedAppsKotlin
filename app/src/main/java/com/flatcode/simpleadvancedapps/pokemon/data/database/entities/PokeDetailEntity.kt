package com.flatcode.simpleadvancedapps.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItemDetails

@Entity(tableName = "pokemon_detail_table")
data class PokeDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_int") val idInt: Int,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "hp") val hp: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "special_attack") val specialAttack: Int,
    @ColumnInfo(name = "special_defense") val specialDefense: Int,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "types") val types: String,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "height") val height: Double
)

fun PokeItemDetails.toDatabase(idInt: Int) = PokeDetailEntity(
    idInt = idInt,
    id = id,
    name = name,
    img = img,
    hp = hp,
    attack = attack,
    defense = defense,
    specialAttack = specialAttack,
    specialDefense = specialDefense,
    speed = speed,
    types = types.joinToString(","),
    weight = weight,
    height = height
)

fun PokeDetailEntity.toDomain() = PokeItemDetails(
    id = id,
    name = name,
    img = img,
    hp = hp,
    attack = attack,
    defense = defense,
    specialAttack = specialAttack,
    specialDefense = specialDefense,
    speed = speed,
    types = types.split(","),
    weight = weight,
    height = height
)