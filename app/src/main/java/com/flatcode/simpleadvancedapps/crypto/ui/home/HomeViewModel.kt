package com.flatcode.simpleadvancedapps.crypto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _cryptoList = MutableStateFlow<List<Data>>(emptyList())
    val cryptoList: StateFlow<List<Data>> = _cryptoList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentStartOffset = 1
    private val limitPerPage = 10

    fun isFirstPage(): Boolean = currentStartOffset == 1

    fun getData(apiKey: String, limit: String) {
        if (_cryptoList.value.isNotEmpty() && isFirstPage()) return

        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getData(apiKey, limit, currentStartOffset.toString())) {
                is NetworkResult.Success -> {
                    result.data.data?.let { newItems ->
                        _cryptoList.value += newItems
                    }
                }

                is NetworkResult.Error -> {
                    _error.value = result.message
                }

                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun loadNextPage(apiKey: String) {
        currentStartOffset += limitPerPage
        getData(apiKey, limitPerPage.toString())
    }
}