package com.flatcode.simpleadvancedapps.countries.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.flatcode.simpleadvancedapps.countries.Model.Country
import com.flatcode.simpleadvancedapps.countries.Service.CountryAPIService
import com.flatcode.simpleadvancedapps.countries.Service.CountryDatabase
import com.flatcode.simpleadvancedapps.countries.Util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()

    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showCountries(countryL: List<Country>) {
        countries.value = countryL
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())

            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i += 1
            }
            showCountries(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}