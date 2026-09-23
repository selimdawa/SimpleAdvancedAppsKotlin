package com.flatcode.simpleadvancedapps.dogs.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): DogApi

    @GET("{breed}/{subBreed}/images")
    suspend fun getSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String,
    ): DogApi
}

@Parcelize
data class DogApi(
    @SerializedName("message") val images: List<String>
) : Parcelable