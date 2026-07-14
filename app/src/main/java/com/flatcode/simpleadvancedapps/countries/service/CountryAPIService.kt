package com.flatcode.simpleadvancedapps.countries.service

import com.flatcode.simpleadvancedapps.utils.Constants
import com.flatcode.simpleadvancedapps.countries.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_COUNTRY)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    suspend fun getData(): List<Country> {
        return api.getCountries()
    }
}