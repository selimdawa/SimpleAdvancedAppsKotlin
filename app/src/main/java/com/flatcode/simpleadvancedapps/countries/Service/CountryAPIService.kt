package com.flatcode.simpleadvancedapps.countries.Service

import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.countries.Model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(DATA.BASE_URL_COUNTRY)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getData(): Single<List<Country>> {
        return api.getCountries()
    }
}