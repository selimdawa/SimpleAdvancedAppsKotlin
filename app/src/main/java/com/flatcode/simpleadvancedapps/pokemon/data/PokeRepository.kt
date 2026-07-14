package com.flatcode.simpleadvancedapps.pokemon.data

import com.flatcode.simpleadvancedapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.toDatabase
import com.flatcode.simpleadvancedapps.pokemon.data.database.entities.toDomain
import com.flatcode.simpleadvancedapps.pokemon.data.network.ApiService
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItemDetails
import com.flatcode.simpleadvancedapps.pokemon.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: ApiService,
    private val pokeDao: PokeDao
) {

    val pokemonListFlow: Flow<List<PokeItem>> = pokeDao.getAllPokemon().map { entities ->
        entities.map { it.toDomain() }
    }.flowOn(Dispatchers.IO)

    suspend fun fetchAndStorePokemon() {
        val response = api.getPokemon()
        if (response.isNotEmpty()) {
            val entities = response.map { it.toDomain().toDatabase() }
            pokeDao.clearTable()
            pokeDao.insertAll(entities)
        }
    }

    suspend fun getPokeDetails(id: Int): PokeItemDetails? {
        val localDetails = pokeDao.getPokemonDetails(id)
        if (localDetails != null) return localDetails.toDomain()

        val response = api.getDetailsPokemon(id)
        return response?.toDomain()?.also { domain ->
            pokeDao.insertPokemonDetail(domain.toDatabase(id))
        }
    }
}