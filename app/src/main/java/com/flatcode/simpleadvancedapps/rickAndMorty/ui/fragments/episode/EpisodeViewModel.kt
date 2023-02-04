package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.episode

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val repository: EpisodeRepository) :
    BaseViewModel() {

    fun fetchEpisode(page: Int) = repository.fetchEpisode(page)
}