package com.flatcode.simpleadvancedapps.dogs.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(value = "akita/images")
    suspend fun getListImg(): DogApi

    @GET(value = "{breed}/images")
    suspend fun getListImg(@Path("breed") breed: String): DogApi

    @GET(value = "{breed}/{subBreed}/images")
    suspend fun getListImg(@Path("breed") breed: String, @Path("subBreed") subBreed: String): DogApi
}

data class DogApi(
    @SerializedName("message") val images: List<String>
)