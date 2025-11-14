package com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseRepository
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.LocationApiService
import javax.inject.Inject

class LocationRepository @Inject constructor(private val service: LocationApiService) :
    BaseRepository() {

    fun fetchLocation(page: Int) = doRequest {
        service.fetchLocation(page)
    }
}