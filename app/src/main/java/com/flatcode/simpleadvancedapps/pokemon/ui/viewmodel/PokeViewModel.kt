package com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pokemon.domain.GetPokemon
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val getPokemon: GetPokemon
) : ViewModel() {

    val pokemonList: LiveData<List<PokeItem>> = getPokemon.pokemonList.asLiveData()

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        refreshPokemon()
    }

    fun refreshPokemon() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                getPokemon.refresh()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }
}