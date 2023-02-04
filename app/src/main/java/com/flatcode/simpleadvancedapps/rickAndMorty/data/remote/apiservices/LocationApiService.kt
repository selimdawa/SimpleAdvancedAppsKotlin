package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices

import com.example.simpleadvancedapps.models.location.LocationModel
import com.flatcode.simpleadvancedapps.rickAndMorty.models.RickAndMortyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {

    @GET("location")
    suspend fun fetchLocation(
        @Query("page") page: Int,
    ): Response<RickAndMortyResponse<LocationModel>>
}