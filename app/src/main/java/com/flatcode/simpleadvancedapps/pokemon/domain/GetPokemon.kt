package com.flatcode.simpleadvancedapps.pokemon.domain

import com.flatcode.simpleadvancedapps.pokemon.data.PokeRepository
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemon @Inject constructor(private val repository: PokeRepository) {

    val pokemonList: Flow<List<PokeItem>> = repository.pokemonListFlow

    suspend fun refresh() {
        repository.fetchAndStorePokemon()
    }
}