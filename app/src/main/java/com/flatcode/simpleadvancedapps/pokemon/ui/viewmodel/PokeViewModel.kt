package com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pokemon.domain.GetPokemon
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val getPokemon: GetPokemon
) : ViewModel() {

    val pokemonList: StateFlow<List<PokeItem>> = getPokemon.pokemonList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _status = MutableStateFlow<ApiStatus?>(null)
    val status: StateFlow<ApiStatus?> = _status

    init {
        refreshPokemon()
    }

    fun refreshPokemon() {
        _status.value = ApiStatus.LOADING
        Timber.d("State updated: status = ApiStatus.LOADING")
        viewModelScope.launch {
            try {
                getPokemon.refresh()
                _status.value = ApiStatus.DONE
                Timber.d("State updated: status = ApiStatus.DONE")
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e, "State updated: status = ApiStatus.ERROR")
            }
        }
    }
}