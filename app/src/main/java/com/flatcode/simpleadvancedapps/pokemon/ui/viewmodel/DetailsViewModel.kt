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

    val pokeDetails: StateFlow<PokeItemDetails?>
        field = MutableStateFlow(null)

    val status: StateFlow<ApiStatusDetail?>
        field = MutableStateFlow(null)

    private var currentId: Int = -1

    fun getPokemonDetails(id: Int) {
        if (id == -1 || id == currentId) return

        currentId = id
        (status as MutableStateFlow).value = ApiStatusDetail.LOADING
        Timber.d("State updated: status = ApiStatusDetail.LOADING")
        viewModelScope.launch {
            try {
                val result = getDetails.fromPokemon(id)
                if (result != null) {
                    (pokeDetails as MutableStateFlow).value = result
                    (status as MutableStateFlow).value = ApiStatusDetail.DONE
                    Timber.d("State updated: pokeDetails = $result, status = ApiStatusDetail.DONE")
                } else {
                    (status as MutableStateFlow).value = ApiStatusDetail.ERROR
                    Timber.d("State updated: status = ApiStatusDetail.ERROR")
                }
            } catch (e: Exception) {
                (status as MutableStateFlow).value = ApiStatusDetail.ERROR
                Timber.e(e, "State updated: status = ApiStatusDetail.ERROR")
            }
        }
    }
}