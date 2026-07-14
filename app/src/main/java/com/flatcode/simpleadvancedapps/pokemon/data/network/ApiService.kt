package com.flatcode.simpleadvancedapps.pokemon.data.network

import com.flatcode.simpleadvancedapps.pokemon.data.model.PokeModel
import com.flatcode.simpleadvancedapps.pokemon.data.model.PokeModelDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getPokemon(): List<PokeModel> {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getListPokemon()
            response.body()?.pokemon ?: emptyList()
        }
    }

    suspend fun getDetailsPokemon(id: Int): PokeModelDetails? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getDetailsPokemon(id)
            response.body()
        }
    }
}