package com.flatcode.simpleadvancedapps.poke.core

import com.flatcode.simpleadvancedapps.Unit.DATA
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(DATA.BASE_URL_POKE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}