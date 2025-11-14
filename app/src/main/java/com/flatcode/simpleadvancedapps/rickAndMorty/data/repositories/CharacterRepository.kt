package com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseRepository
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.CharacterApiService
import javax.inject.Inject

class CharacterRepository @Inject constructor(val service: CharacterApiService) :
    BaseRepository() {

    fun fetchCharacter(page: Int) = doRequest {
        service.fetchCharacter(page)
    }
}