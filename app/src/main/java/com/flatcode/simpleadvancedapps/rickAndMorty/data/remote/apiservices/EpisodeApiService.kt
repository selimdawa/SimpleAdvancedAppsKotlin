package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices

import com.flatcode.simpleadvancedapps.rickAndMorty.models.RickAndMortyResponse
import com.flatcode.simpleadvancedapps.rickAndMorty.models.episode.EpisodeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApiService {

    @GET("episode")
    suspend fun fetchEpisode(
        @Query("page") page: Int,
    ): Response<RickAndMortyResponse<EpisodeModel>>
}