package com.flatcode.simpleadvancedapps.countries.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.countries.model.Country
import com.flatcode.simpleadvancedapps.countries.service.CountryDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application, private val countryDao: CountryDAO,
) : AndroidViewModel(application) {
    private val _countryState = MutableStateFlow<Country?>(null)
    val countryState: StateFlow<Country?> = _countryState

    fun getDataFromRoom(uuid: Int) {
        viewModelScope.launch {
            val country = countryDao.getCountry(uuid)
            _countryState.value = country
            Timber.d("State updated: countryState = $country")
        }
    }
}