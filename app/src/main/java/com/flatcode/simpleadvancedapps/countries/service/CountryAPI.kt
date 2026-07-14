package com.flatcode.simpleadvancedapps.countries.service

import com.flatcode.simpleadvancedapps.utils.Constants
import com.flatcode.simpleadvancedapps.countries.model.Country
import retrofit2.http.GET

interface CountryAPI {
    @GET(Constants.COUNTRY_GSON)
    suspend fun getCountries(): List<Country>
}