package com.flatcode.simpleadvancedapps.poke.domain

import com.flatcode.simpleadvancedapps.poke.data.PokeRepository
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItemDetails

class GetDetails {

    private val repository = PokeRepository()

    suspend fun fromPokemon(id: Int): PokeItemDetails? {
        return repository.getPokeDetails(id)
    }
}