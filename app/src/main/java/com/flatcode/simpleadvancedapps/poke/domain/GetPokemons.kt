package com.flatcode.simpleadvancedapps.poke.domain

import com.flatcode.simpleadvancedapps.poke.data.PokeRepository
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItem

class GetPokemons {

    private val repository = PokeRepository()

    suspend fun listAll(): List<PokeItem> {
        return repository.getAllPokemons()
    }
}