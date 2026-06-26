package com.flatcode.simpleadvancedapps.poke.domain.model

import com.flatcode.simpleadvancedapps.poke.data.model.PokeModelDetails
import com.flatcode.simpleadvancedapps.poke.data.model.Types
import java.util.Locale

data class PokeItemDetails(
    val id: String,
    val name: String,
    val img: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val types: List<String>,
    val weight: Double,
    val height: Double
)

fun PokeModelDetails.toDomain(): PokeItemDetails {
    val formattedId = "N° ${id.toString().padStart(3, '0')}"
    val capitalizedName = replaceFirstChar(name)
    val imgUrl = sprites.other.officialArtwork.img
    val hpValue = pokemonDetails.getOrNull(0)?.statValue ?: 0
    val attackValue = pokemonDetails.getOrNull(1)?.statValue ?: 0
    val defenseValue = pokemonDetails.getOrNull(2)?.statValue ?: 0
    val specialAttackValue = pokemonDetails.getOrNull(3)?.statValue ?: 0
    val specialDefenseValue = pokemonDetails.getOrNull(4)?.statValue ?: 0
    val speedValue = pokemonDetails.getOrNull(5)?.statValue ?: 0
    val typeList = getTypes(types)
    val weightInKg = weight / 10.0
    val heightInMeters = height / 10.0

    return PokeItemDetails(
        id = formattedId,
        name = capitalizedName,
        img = imgUrl,
        hp = hpValue,
        attack = attackValue,
        defense = defenseValue,
        specialAttack = specialAttackValue,
        specialDefense = specialDefenseValue,
        speed = speedValue,
        types = typeList,
        weight = weightInKg,
        height = heightInMeters
    )
}

private fun getTypes(types: List<Types>): List<String> {
    return types.map { replaceFirstChar(it.type.name) }
}

private fun replaceFirstChar(t: String): String {
    return t.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}