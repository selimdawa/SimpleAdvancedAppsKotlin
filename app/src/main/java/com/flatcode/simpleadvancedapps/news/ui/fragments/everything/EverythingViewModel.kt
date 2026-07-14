package com.flatcode.simpleadvancedapps.news.ui.fragments.everything

import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.news.base.BaseViewModel
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.data.repositories.EverythingRepository
import com.flatcode.simpleadvancedapps.news.models.EverythingNewsItem
import com.flatcode.simpleadvancedapps.news.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EverythingViewModel @Inject constructor(private val repository: EverythingRepository) :
    BaseViewModel() {

    private val _everythingState =
        MutableStateFlow<Resource<NewsResponse<EverythingNewsItem>>>(Resource.Loading)
    val everythingState = _everythingState.asStateFlow()

    init {
        fetchEverything()
    }

    private fun fetchEverything() {
        viewModelScope.launch {
            repository.fetchEverything().collect {
                _everythingState.value = it
            }
        }
    }
}