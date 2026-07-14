package com.flatcode.simpleadvancedapps.pokemon.domain

import com.flatcode.simpleadvancedapps.pokemon.data.PokeRepository
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItemDetails
import javax.inject.Inject

class GetDetails @Inject constructor(private val repository: PokeRepository) {

    suspend fun fromPokemon(id: Int): PokeItemDetails? {
        return repository.getPokeDetails(id)
    }
}