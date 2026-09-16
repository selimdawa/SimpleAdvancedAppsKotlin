package com.flatcode.simpleadvancedapps.countries.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.countries.model.Country
import com.flatcode.simpleadvancedapps.countries.service.CountryAPI
import com.flatcode.simpleadvancedapps.countries.service.CountryDAO
import com.flatcode.simpleadvancedapps.countries.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application, private val countryApi: CountryAPI,
    private val countryDao: CountryDAO, private val customSharedPreferences: CustomDataStore,
) : AndroidViewModel(application) {

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private val _countries = MutableStateFlow(emptyList<Country>())
    val countries: StateFlow<List<Country>> = _countries

    private val _countryError = MutableStateFlow(false)
    val countryError: StateFlow<Boolean> = _countryError

    private val _countryLoading = MutableStateFlow(false)
    val countryLoading: StateFlow<Boolean> = _countryLoading

    fun refreshData() {
        viewModelScope.launch {
            val updateTime = customSharedPreferences.getTimeSync()
            if (updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
                getDataFromSQLite()
            } else {
                getDataFromAPI()
            }
        }
    }

    private fun getDataFromSQLite() {
        viewModelScope.launch {
            val countries = countryDao.getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.countries_from_sqlite), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        _countryLoading.value = true
        Timber.d("State updated: countryLoading = true")
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    countryApi.getCountries()
                }
                storeInSQLite(list)
                Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.countries_from_api), Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                _countryError.value = true
                _countryLoading.value = false
                Timber.e(e, "Error fetching countries from API. State updated: countryError = true, countryLoading = false")
            }
        }
    }

    private fun showCountries(countryL: List<Country>) {
        _countries.value = countryL
        _countryError.value = false
        _countryLoading.value = false
        Timber.d("State updated: countries = $countryL, countryError = false, countryLoading = false")
    }

    private fun storeInSQLite(list: List<Country>) {
        viewModelScope.launch {
            countryDao.deleteAllCountries()
            val listLong = countryDao.insertAll(*list.toTypedArray())

            list.forEachIndexed { index, country ->
                country.uuid = listLong[index].toInt()
            }

            customSharedPreferences.saveTime(System.nanoTime())
            showCountries(list)
        }
    }
}