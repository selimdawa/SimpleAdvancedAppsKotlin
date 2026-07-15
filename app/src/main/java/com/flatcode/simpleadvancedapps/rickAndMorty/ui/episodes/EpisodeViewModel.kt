package com.flatcode.simpleadvancedapps.rickAndMorty.ui.episodes

import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel() {

    fun fetchEpisodes(page: Int) = repository.fetchEpisodes(page)
}