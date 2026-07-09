package com.flatcode.simpleadvancedapps.countries.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.flatcode.simpleadvancedapps.countries.model.Country
import com.flatcode.simpleadvancedapps.countries.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}