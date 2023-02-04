package com.flatcode.simpleadvancedapps.rickAndMorty.ui.fragments.characters

import com.flatcode.simpleadvancedapps.rickAndMorty.base.BaseViewModel
import com.flatcode.simpleadvancedapps.rickAndMorty.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CharacterRepository) :
    BaseViewModel() {

    fun fetchCharacters(page: Int) = repository.fetchCharacter(page)
}