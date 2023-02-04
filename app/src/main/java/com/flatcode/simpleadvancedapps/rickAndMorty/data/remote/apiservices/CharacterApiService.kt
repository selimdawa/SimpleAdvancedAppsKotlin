package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices

import com.flatcode.simpleadvancedapps.rickAndMorty.models.RickAndMortyResponse
import com.flatcode.simpleadvancedapps.rickAndMorty.models.character.CharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun fetchCharacter(
        @Query("page") page: Int,
    ): Response<RickAndMortyResponse<CharacterModel>>
}