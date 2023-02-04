package com.flatcode.simpleadvancedapps.countries.Service

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.countries.Model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET(DATA.COUNTRY_GSON)
    fun getCountries(): Single<List<Country>>
}