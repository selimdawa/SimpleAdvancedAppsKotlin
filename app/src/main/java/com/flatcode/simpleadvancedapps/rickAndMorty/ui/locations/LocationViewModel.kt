package com.flatcode.simpleadvancedapps.rickAndMorty.ui.locations

import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel() {

    fun fetchLocations(page: Int) = repository.fetchLocations(page)
}