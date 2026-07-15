package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote

import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.CharacterModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.EpisodeModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.LocationModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.models.RickAndMortyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: Int): Response<RickAndMortyResponse<CharacterModel>>

    @GET("episode")
    suspend fun fetchEpisodes(@Query("page") page: Int): Response<RickAndMortyResponse<EpisodeModel>>

    @GET("location")
    suspend fun fetchLocations(@Query("page") page: Int): Response<RickAndMortyResponse<LocationModel>>
}