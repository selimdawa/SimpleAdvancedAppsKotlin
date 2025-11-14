package com.flatcode.simpleadvancedapps.meals.retrofit

import com.flatcode.simpleadvancedapps.Unit.DATA
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: MealApi by lazy {
        Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_MEALS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi:: class.java)
    }
}