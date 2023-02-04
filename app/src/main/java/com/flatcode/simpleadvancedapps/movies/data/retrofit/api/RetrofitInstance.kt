package com.flatcode.simpleadvancedapps.movies.data.retrofit.api

import com.flatcode.simpleadvancedapps.Unit.DATA
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_MOVIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}