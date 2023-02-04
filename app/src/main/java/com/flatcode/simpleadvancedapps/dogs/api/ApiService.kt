package com.flatcode.simpleadvancedapps.dogs.api

import com.flatcode.simpleadvancedapps.Unit.DATA
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(DATA.BASE_URL_DOGS)
    .build()

interface ApiService {

    @GET(value = "akita/images")
    suspend fun getListImg(): DogApi

    @GET(value = "{breed}/images")
    suspend fun getListImg(@Path("breed") breed: String): DogApi

    @GET(value = "{breed}/{subBreed}/images")
    suspend fun getListImg(@Path("breed") breed: String, @Path("subBreed") subBreed: String): DogApi
}

object DogApiService {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}