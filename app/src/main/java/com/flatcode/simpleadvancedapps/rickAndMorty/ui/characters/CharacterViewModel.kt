package com.flatcode.simpleadvancedapps.rickAndMorty.ui.characters

import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel() {

    fun fetchCharacters(page: Int) = repository.fetchCharacters(page)
}