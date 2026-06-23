package com.flatcode.simpleadvancedapps.crypto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _cryptoList = MutableLiveData<List<Data>?>()
    val cryptoList: LiveData<List<Data>?> get() = _cryptoList

    val isLoading = MutableLiveData(true)
    val onError = MutableLiveData<String?>()

    private val accumulatedCryptoList = mutableListOf<Data>()
    private var currentStartOffset = 1
    private val limitPerPage = 10

    fun isFirstPage(): Boolean = currentStartOffset == 1

    fun getData(apiKey: String, limit: String) {
        if (accumulatedCryptoList.isNotEmpty() && isFirstPage()) {
            _cryptoList.value = accumulatedCryptoList
            isLoading.value = false
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            when (val request = repository.getData(apiKey, limit, currentStartOffset.toString())) {
                is NetworkResult.Success -> {
                    request.data.data?.let { newItems ->
                        accumulatedCryptoList.addAll(newItems)
                        _cryptoList.value = accumulatedCryptoList
                    }
                    isLoading.value = false
                }
                is NetworkResult.Error -> {
                    onError.value = request.message
                    isLoading.value = false
                }
            }
        }
    }

    fun loadNextPage(apiKey: String) {
        currentStartOffset += limitPerPage
        getData(apiKey, limitPerPage.toString())
    }
}