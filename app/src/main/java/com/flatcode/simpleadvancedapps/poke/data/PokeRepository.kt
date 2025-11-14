package com.flatcode.simpleadvancedapps.poke.data

import com.flatcode.simpleadvancedapps.poke.data.network.ApiService
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItem
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItemDetails
import com.flatcode.simpleadvancedapps.poke.domain.model.toDomain

class PokeRepository {

    private val api = ApiService()

    suspend fun getAllPokemons(): List<PokeItem> {
        val response = api.getPokemons()
        return response.map { it.toDomain() }
    }

    suspend fun getPokeDetails(id: Int): PokeItemDetails? {
        val response = api.getDetailsPokemon(id)
        return response?.toDomain()
    }
}