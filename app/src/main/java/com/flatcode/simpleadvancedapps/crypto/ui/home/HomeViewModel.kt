package com.flatcode.simpleadvancedapps.crypto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val cryptoList: StateFlow<List<Data>>
        field = MutableStateFlow<List<Data>>(emptyList())

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    private var currentStartOffset = 1
    private val limitPerPage = 10

    fun isFirstPage(): Boolean = currentStartOffset == 1

    fun getData(apiKey: String, limit: String) {
        if (cryptoList.value.isNotEmpty() && isFirstPage()) return

        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getData(apiKey, limit, currentStartOffset.toString())) {
                is NetworkResult.Success -> {
                    result.data.data?.let { newItems ->
                        cryptoList.value += newItems
                    }
                }

                is NetworkResult.Error -> {
                    error.value = result.message
                }

                else -> {}
            }
            isLoading.value = false
        }
    }

    fun loadNextPage(apiKey: String) {
        currentStartOffset += limitPerPage
        getData(apiKey, limitPerPage.toString())
    }
}