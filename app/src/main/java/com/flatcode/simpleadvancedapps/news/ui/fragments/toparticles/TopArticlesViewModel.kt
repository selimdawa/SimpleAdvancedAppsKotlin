package com.flatcode.simpleadvancedapps.news.ui.fragments.toparticles

import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.news.base.BaseViewModel
import com.flatcode.simpleadvancedapps.news.common.Resource
import com.flatcode.simpleadvancedapps.news.data.repositories.EverythingRepository
import com.flatcode.simpleadvancedapps.news.models.NewsResponse
import com.flatcode.simpleadvancedapps.news.models.TopArticlesNewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopArticlesViewModel @Inject constructor(private val repository: EverythingRepository) :
    BaseViewModel() {

    private val _topArticlesState =
        MutableStateFlow<Resource<NewsResponse<TopArticlesNewsItem>>>(Resource.Loading)
    val topArticlesState = _topArticlesState.asStateFlow()

    init {
        fetchTopArticles()
    }

    private fun fetchTopArticles() {
        viewModelScope.launch {
            repository.fetchTopArticles().collect {
                _topArticlesState.value = it
            }
        }
    }
}