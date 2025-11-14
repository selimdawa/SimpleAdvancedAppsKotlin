package com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseRepository
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.EpisodeApiService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val service: EpisodeApiService) :
    BaseRepository() {

    fun fetchEpisode(page: Int) = doRequest {
        service.fetchEpisode(page)
    }
}