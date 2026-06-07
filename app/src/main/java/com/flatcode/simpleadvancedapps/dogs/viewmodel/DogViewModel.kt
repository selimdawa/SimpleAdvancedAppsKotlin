package com.flatcode.simpleadvancedapps.dogs.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.dogs.model.DogApi
import com.flatcode.simpleadvancedapps.dogs.model.DogApiService
import kotlinx.coroutines.launch

enum class DogApiStatus { LOADING, ERROR, DONE, START }

class DogViewModel : ViewModel() {

    private var _breedsList = MutableLiveData<Array<String>>()
    val breedsList: LiveData<Array<String>> get() = _breedsList

    fun setBreedsList(list: Array<String>) {
        _breedsList.value = list
    }

    private val _status = MutableLiveData<DogApiStatus>()
    val status: LiveData<DogApiStatus> get() = _status

    init {
        _status.value = DogApiStatus.START
    }

    private val _photosDog = MutableLiveData<List<String>>()
    val photosDog: LiveData<List<String>> get() = _photosDog

    fun getDogPhotosList(context: Context, item: String) {
        if (!isInternetAvailable(context)) {
            _photosDog.value = listOf()
            _status.value = DogApiStatus.ERROR
            return
        }

        _status.value = DogApiStatus.LOADING
        viewModelScope.launch {
            try {
                val list = if (DATA.SPACE in item) {
                    connection2(item)
                } else {
                    connection1(item)
                }
                _photosDog.value = converter(list)
                _status.value = DogApiStatus.DONE
            } catch (e: Exception) {
                _status.value = DogApiStatus.ERROR
                _photosDog.value = listOf()
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        // Checks if basic internet capability is active, completely ignoring transport types or proxies
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private suspend fun connection1(t: String): DogApi {
        val url = textLowercase(t)
        return DogApiService.retrofitService.getListImg(url)
    }

    private suspend fun connection2(t: String): DogApi {
        val list = t.split(DATA.SPACE)
        val breed = textLowercase(list[0])
        val subBred = textLowercase(list[1])
        return DogApiService.retrofitService.getListImg(breed, subBred)
    }

    private fun textLowercase(text: String): String {
        return text.lowercase()
    }

    private fun converter(list: DogApi): List<String> {
        val listDog = mutableListOf<String>()
        list.images.forEach { item ->
            listDog.add(item)
        }
        return listDog
    }
}