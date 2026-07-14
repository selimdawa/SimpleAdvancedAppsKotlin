package com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pokemon.domain.GetDetails
import com.flatcode.simpleadvancedapps.pokemon.domain.model.PokeItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

enum class ApiStatusDetail { LOADING, ERROR, DONE }

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetails: GetDetails
) : ViewModel() {

    private var _pokeDetails = MutableLiveData<PokeItemDetails>()
    val pokeDetails: LiveData<PokeItemDetails> get() = _pokeDetails

    private var _status = MutableLiveData<ApiStatusDetail>()
    val status: LiveData<ApiStatusDetail>
        get() = _status

    private var currentId: Int = -1

    fun getPokemonDetails(id: Int) {
        if (id == -1 || id == currentId) return

        currentId = id
        _status.value = ApiStatusDetail.LOADING
        viewModelScope.launch {
            try {
                val result = getDetails.fromPokemon(id)
                if (result != null) {
                    _pokeDetails.value = result
                    _status.value = ApiStatusDetail.DONE
                } else {
                    _status.value = ApiStatusDetail.ERROR
                }
            } catch (e: Exception) {
                _status.value = ApiStatusDetail.ERROR
                Timber.e(e)
            }
        }
    }
}