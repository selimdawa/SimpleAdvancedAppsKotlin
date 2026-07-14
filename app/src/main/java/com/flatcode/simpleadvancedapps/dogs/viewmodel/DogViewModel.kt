package com.flatcode.simpleadvancedapps.dogs.viewmodel

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.dogs.service.ApiService
import com.flatcode.simpleadvancedapps.dogs.service.DogApi
import com.flatcode.simpleadvancedapps.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DogApiStatus { LOADING, ERROR, DONE, START }

@HiltViewModel
class DogViewModel @Inject constructor(
    private val apiService: ApiService, private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _breedsList = MutableLiveData<Array<String>>()
    val breedsList: LiveData<Array<String>> get() = _breedsList

    fun setBreedsList(list: Array<String>) {
        _breedsList.value = list
    }

    private val _status = MutableLiveData(DogApiStatus.START)
    val status: LiveData<DogApiStatus> get() = _status

    private val _photosDog = MutableLiveData<List<String>>()
    val photosDog: LiveData<List<String>> get() = _photosDog

    fun getDogPhotosList(item: String) {
        if (!isInternetAvailable()) {
            _photosDog.value = emptyList()
            _status.value = DogApiStatus.ERROR
            return
        }

        _status.value = DogApiStatus.LOADING
        viewModelScope.launch {
            try {
                val list = if (Constants.SPACE in item) connection2(item) else connection1(item)
                _photosDog.value = converter(list)
                _status.value = DogApiStatus.DONE
            } catch (_: Exception) {
                _status.value = DogApiStatus.ERROR
                _photosDog.value = emptyList()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private suspend fun connection1(t: String): DogApi {
        return apiService.getListImg(t.lowercase())
    }

    private suspend fun connection2(t: String): DogApi {
        val list = t.split(Constants.SPACE)
        val breed = list[0].lowercase()
        val subBreed = list[1].lowercase()
        return apiService.getListImg(breed, subBreed)
    }

    private fun converter(list: DogApi): List<String> {
        return list.images.toList()
    }
}