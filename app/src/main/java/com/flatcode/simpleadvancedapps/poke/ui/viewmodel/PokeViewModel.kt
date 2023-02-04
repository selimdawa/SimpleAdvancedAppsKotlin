package com.flatcode.simpleadvancedapps.poke.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.poke.domain.GetPokemons
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItem
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }

class PokeViewModel : ViewModel() {

    private var _pokemonList = MutableLiveData<List<PokeItem>>()
    val pokemonList: LiveData<List<PokeItem>>
        get() = _pokemonList

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getAllPokemon()
    }

    private fun getAllPokemon() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _pokemonList.value = GetPokemons().listAll()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e.message)
                _pokemonList.value = listOf()
            }
        }
    }
}