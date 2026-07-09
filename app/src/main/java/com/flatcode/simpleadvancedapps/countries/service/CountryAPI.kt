package com.flatcode.simpleadvancedapps.countries.service

import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.countries.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET(DATA.COUNTRY_GSON)
    fun getCountries(): Single<List<Country>>
}