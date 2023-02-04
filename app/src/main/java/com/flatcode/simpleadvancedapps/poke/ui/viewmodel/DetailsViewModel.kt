package com.flatcode.simpleadvancedapps.poke.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.poke.domain.GetDetails
import com.flatcode.simpleadvancedapps.poke.domain.model.PokeItemDetails
import com.flatcode.simpleadvancedapps.poke.ui.view.DetailFragment
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatusDetail { LOADING, ERROR, DONE }

class DetailsViewModel() : ViewModel() {

    private var _pokeDetails = MutableLiveData<PokeItemDetails>()
    val pokeDetails: LiveData<PokeItemDetails> get() = _pokeDetails

    private var _status = MutableLiveData<ApiStatusDetail>()
    val status: LiveData<ApiStatusDetail>
        get() = _status

    init {
        getPokemonDetails(DetailFragment.idP)
    }

    private fun getPokemonDetails(id: Int) {
        _status.value = ApiStatusDetail.LOADING
        viewModelScope.launch {
            try {
                _pokeDetails.value = GetDetails().fromPokemon(id)
                _status.value = ApiStatusDetail.DONE
            } catch (e: Exception) {
                _status.value = ApiStatusDetail.ERROR
                Timber.d(e.message)
            }
        }
    }
}