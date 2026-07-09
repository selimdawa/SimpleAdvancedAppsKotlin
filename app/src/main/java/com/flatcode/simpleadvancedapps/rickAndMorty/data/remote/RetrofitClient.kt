package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote

import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.CharacterApiService
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.EpisodeApiService
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.LocationApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val retrofit = Retrofit.Builder().baseUrl(DATA.BASE_URL_RICK_AND_MORTY)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient()).build()

    private fun provideOkHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor(provideLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun provideCharacterApiService(): CharacterApiService = retrofit.create(CharacterApiService::class.java)
    fun provideLocationApiService(): LocationApiService = retrofit.create(LocationApiService::class.java)
    fun provideEpisodeApiService(): EpisodeApiService = retrofit.create(EpisodeApiService::class.java)
}