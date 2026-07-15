package com.flatcode.simpleadvancedapps.rickAndMorty.data.remote

import com.flatcode.simpleadvancedapps.utils.DATA
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(DATA.BASE_URL_RICK_AND_MORTY)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient()).build()

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(provideLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun provideApiService(): ApiService = retrofit.create(ApiService::class.java)
}