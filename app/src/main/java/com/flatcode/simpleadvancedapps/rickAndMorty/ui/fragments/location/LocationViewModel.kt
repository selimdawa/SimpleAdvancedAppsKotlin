package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.location

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: LocationRepository) :
    BaseViewModel() {

    fun fetchLocation(page: Int) = repository.fetchLocation(page)
}