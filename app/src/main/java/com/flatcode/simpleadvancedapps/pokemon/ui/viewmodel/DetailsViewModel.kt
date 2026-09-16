package com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pokemon.domain.GetDetails
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

enum class ApiStatusDetail { LOADING, ERROR, DONE }

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetails: GetDetails
) : ViewModel() {

    private val _pokeDetails = MutableStateFlow<PokeItemDetails?>(null)
    val pokeDetails: StateFlow<PokeItemDetails?> = _pokeDetails

    private val _status = MutableStateFlow<ApiStatusDetail?>(null)
    val status: StateFlow<ApiStatusDetail?> = _status

    private var currentId: Int = -1

    fun getPokemonDetails(id: Int) {
        if (id == -1 || id == currentId) return

        currentId = id
        _status.value = ApiStatusDetail.LOADING
        Timber.d("State updated: status = ApiStatusDetail.LOADING")
        viewModelScope.launch {
            try {
                val result = getDetails.fromPokemon(id)
                if (result != null) {
                    _pokeDetails.value = result
                    _status.value = ApiStatusDetail.DONE
                    Timber.d("State updated: pokeDetails = $result, status = ApiStatusDetail.DONE")
                } else {
                    _status.value = ApiStatusDetail.ERROR
                    Timber.d("State updated: status = ApiStatusDetail.ERROR")
                }
            } catch (e: Exception) {
                _status.value = ApiStatusDetail.ERROR
                Timber.e(e, "State updated: status = ApiStatusDetail.ERROR")
            }
        }
    }
}