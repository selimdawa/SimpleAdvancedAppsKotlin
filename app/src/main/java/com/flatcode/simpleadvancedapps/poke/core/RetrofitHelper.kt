package com.flatcode.simpleadvancedapps.poke.core

import com.flatcode.simpleadvancedapps.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_POKE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}